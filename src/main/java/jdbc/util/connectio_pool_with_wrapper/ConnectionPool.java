package jdbc.util.connectio_pool_with_wrapper;


import jdbc.util.ConnectionManager;
import jdbc.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);

    private static final String DB_URL = "DB.URL";
    private static final String DB_USERNAME = "DB.USERNAME";
    private static final String DB_PASSWORD = "DB.PASSWORD";
    private static final String POOL_SIZE_KEY = "DB.POOL.SIZE";
    private static final Integer DEFAULT_POOL_SIZE = 10;

    private static BlockingQueue<ConnectionWrapper> pool;

    static {
        loadDriver(); // added for older java versions (before 1.8)
        initConnectionPool();
    }

    private ConnectionPool() {
        // private constructor
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            ConnectionWrapper conn = open();
            pool.add(conn);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static ConnectionWrapper open() {
        try {
            Connection connection = DriverManager.getConnection(
                    PropertiesUtil.get(DB_URL),
                    PropertiesUtil.get(DB_USERNAME),
                    PropertiesUtil.get(DB_PASSWORD)
            );
            return new ConnectionWrapper(connection, pool);
        } catch (SQLException e) {
            LOGGER.error("Failed to create db connection with credentials [URL={} USER={} PASSWORD={}]", DB_URL, DB_USERNAME, DB_PASSWORD);
            throw new RuntimeException(e);
        }
    }

}

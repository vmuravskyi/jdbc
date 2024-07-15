package jdbc.core;

import jdbc.core.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {

        Connection connection = ConnectionManager.open();
        System.out.println(connection.getTransactionIsolation());

    }

}

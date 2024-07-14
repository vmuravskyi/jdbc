package jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Hello world!
 */
public class TestConnection {

    public static void main(String[] args) {
        // jdbc:postgresql://localhost:5432/first_lesson?user=admin@&password=password&ssl=true
//        Class<?> clazz = Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://localhost:5432/first_lesson";
        Properties props = new Properties();
        props.setProperty("user", "admin");
        props.setProperty("password", "password");
        props.setProperty("ssl", "false");

        try {
            Connection conn = DriverManager.getConnection(url, props);
            if (conn.isValid(1)) {
                System.out.printf("[%s] connection successful\n", url);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }

}

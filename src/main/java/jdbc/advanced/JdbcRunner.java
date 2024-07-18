package jdbc.advanced;

import jdbc.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {

    public static void main(String[] args) {

        List<Long> ticketsByFlightId = getFlightsBetween(LocalDateTime.of(2023, 7, 1, 0, 0, 0), LocalDateTime.now());
        System.out.println(ticketsByFlightId);

        checkMetadata();
    }

    private static void checkMetadata() {
        try (Connection connection = ConnectionManager.open()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                System.out.println(catalogs.getString("TABLE_CAT")); // get parameters from metaData.getCatalogs()
                ResultSet schemas = metaData.getSchemas();
                while (schemas.next()) {
                    System.out.println(schemas.getString("TABLE_SCHEM")); // params from metaData.getSchemas();
                    ResultSet tables = metaData.getTables(null, null, "%s", null);
                    while (tables.next()) {
                        String tableName = tables.getString("TABLE_NAME"); // params from metaData.getTables()
                        System.out.println(tableName);
                    }
                }
                System.out.println("**************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Long> getFlightsBetween(LocalDateTime from, LocalDateTime to) {
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;
        List<Long> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // additional settings
            preparedStatement.setFetchSize(5);
            preparedStatement.setQueryTimeout(10);
            preparedStatement.setMaxRows(1000);

            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(from));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(to));
            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static List<Long> getTicketsByFlightId(Long flightId) {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?""";
        List<Long> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, flightId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class)); // in case of null
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}

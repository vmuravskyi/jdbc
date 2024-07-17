package jdbc.core;

import jdbc.core.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {

    public static void main(String[] args) {
//        Long flightId = 2L;
//        List<Long> ticketsByFlightId = getTicketsByFlightId(flightId);
//        System.out.println(ticketsByFlightId);

        List<Long> ticketsByFlightId = getFlightsBetween(LocalDateTime.of(2023, 7, 1, 0, 0, 0), LocalDateTime.now());
        System.out.println(ticketsByFlightId);
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


//    public static void main(String[] args) throws SQLException {
//        String flightId = "2 OR 1 = 1"; // SQL injection in case using createStatement()
////        String flightId = "2 OR 1 = 1; DROP DATABASE info"; // SQL injection
//        List<Long> ticketsByFlightId = getTicketsByFlightId(flightId);
//        System.out.println(ticketsByFlightId);
//    }
//
//
//    private static List<Long> getTicketsByFlightId(String flightId) {
//        String sql = """
//                SELECT id
//                FROM ticket
//                WHERE flight_id = %s"""
//                .formatted(flightId);
//        List<Long> result = new ArrayList<>();
//        try (Connection connection = ConnectionManager.open();
//             Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                result.add(resultSet.getObject("id", Long.class)); // in case of null
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return result;
//    }

}

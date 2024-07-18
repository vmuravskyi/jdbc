package jdbc.advanced;

import jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchRunner {

    public static void main(String[] args) throws SQLException {
        long flightId = 8;
        String deleteFlightSql = String.format("DELETE FROM flight WHERE id = %d", flightId);
        String deleteTicketSql = String.format("DELETE FROM ticket WHERE flight_id = %d", flightId);

        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.addBatch(deleteTicketSql);
            statement.addBatch(deleteFlightSql);

            int[] results = statement.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

    }

}

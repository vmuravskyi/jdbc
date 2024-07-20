package jdbc.dao;

import jdbc.entity.TicketEntity;
import jdbc.exception.DaoException;
import jdbc.util.connectio_pool_with_wrapper.ConnectionPool;

import java.sql.*;
import java.util.Optional;

public class TicketDao {

    private static final TicketDao INSTANCE = new TicketDao();

    private static final String FIND_BY_ID = """
            SELECT
                id,
                passenger_no,
                passenger_name,
                flight_id,
                seat_no,
                cost
            FROM ticket
            WHERE id = ?
            """;

    private static final String CREATE_SQL = """
            INSERT INTO ticket (passenger_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE ticket
            SET passenger_no = ?,
                passenger_name = ?,
                flight_id = ?,
                seat_no = ?,
                cost = ?
            WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id = ?
            """;

    private TicketDao() {
        // private constructor
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    public Optional<TicketEntity> getById(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            TicketEntity ticketEntity = null;
            if (resultSet.next()) {
                ticketEntity = new TicketEntity()
                        .setId(id)
                        .setPassengerNo(resultSet.getString("passenger_no"))
                        .setPassengerName(resultSet.getString("passenger_name"))
                        .setFlightId(resultSet.getLong("flight_id"))
                        .setSeatNo(resultSet.getString("seat_no"))
                        .setCost(resultSet.getBigDecimal("cost"));
            }
            return Optional.ofNullable(ticketEntity);
        } catch (SQLException e) {
            throw new DaoException(String.format("Failed to get ticket by id [%s]", id), e);
        }
    }

    public TicketEntity save(TicketEntity ticketEntity) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ticketEntity.getPassengerNo());
            preparedStatement.setString(2, ticketEntity.getPassengerName());
            preparedStatement.setLong(3, ticketEntity.getFlightId());
            preparedStatement.setString(4, ticketEntity.getSeatNo());
            preparedStatement.setBigDecimal(5, ticketEntity.getCost());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ticketEntity.setId(generatedKeys.getLong("id"));
            }
            return ticketEntity;
        } catch (SQLException e) {
            throw new DaoException("Failed to save ticket", e);
        }
    }

    public TicketEntity update(TicketEntity ticketEntity) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, ticketEntity.getPassengerNo());
            preparedStatement.setString(2, ticketEntity.getPassengerName());
            preparedStatement.setLong(3, ticketEntity.getFlightId());
            preparedStatement.setString(4, ticketEntity.getSeatNo());
            preparedStatement.setBigDecimal(5, ticketEntity.getCost());

            preparedStatement.setLong(6, ticketEntity.getId());
            preparedStatement.executeUpdate();
            return ticketEntity;
        } catch (SQLException e) {
            throw new DaoException("Failed to updated ticket", e);
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete ticket", e);
        }
    }

}

package jdbc.dao;

import jdbc.entity.FlightEntity;
import jdbc.exception.DaoException;
import jdbc.util.connectio_pool_with_wrapper.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, FlightEntity> {

    private static final FlightDao INSTANCE = new FlightDao();

    private static final String FIND_BY_ID = """
            SELECT
                id,
                flight_no,
                departure_date,
                departure_airport_code,
                arrival_date,
                arrival_airport_code,
                aircraft_id,
                status
            FROM FLIGHT
            WHERE id = ?
            """;

    private FlightDao() {
        // private constructor
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }


    @Override
    public FlightEntity save(FlightEntity entity) {
        return null;
    }

    @Override
    public FlightEntity update(FlightEntity entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional<FlightEntity> findById(Long id) {
        try (Connection connection = ConnectionPool.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException("Failed to find flight by id", e);
        }
    }

    public Optional<FlightEntity> findById(Long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            FlightEntity flight = null;
            if (resultSet.next()) {
                flight = new FlightEntity()
                        .setId(resultSet.getLong("id"))
                        .setFlightNo(resultSet.getString("flight_no"))
                        .setDepartureDate(resultSet.getTimestamp("departure_date").toLocalDateTime())
                        .setDepartureAirportCode(resultSet.getString("departure_airport_code"))
                        .setArrivalDate(resultSet.getTimestamp("arrival_date").toLocalDateTime())
                        .setArrivalAirportCode(resultSet.getString("arrival_airport_code"))
                        .setAircraftId(resultSet.getInt("aircraft_id"))
                        .setStatus(resultSet.getString("status"));
            }
            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new DaoException("Failed to find flight by id", e);
        }
    }

    @Override
    public List<FlightEntity> findAll() {
        return List.of();
    }

}

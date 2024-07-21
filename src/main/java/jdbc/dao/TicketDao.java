package jdbc.dao;

import jdbc.entity.FlightEntity;
import jdbc.entity.TicketEntity;
import jdbc.exception.DaoException;
import jdbc.util.connectio_pool_with_wrapper.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao {

    private static final TicketDao INSTANCE = new TicketDao();

    private static final String FIND_BY_ID = """
            SELECT
                ticket.id,
                passenger_no,
                passenger_name,
                flight_id,
                seat_no,
                cost,
                f.flight_no,
                f.status,
                f.aircraft_id,
                f.arrival_airport_code,
                f.arrival_date,
                f.departure_airport_code,
                f.departure_date
            FROM ticket
            JOIN flight f
                ON ticket.flight_id = f.id
            WHERE ticket.id = ?
            """;

    private static final String FIND_ALL = """
            SELECT
                ticket.id,
                passenger_no,
                passenger_name,
                flight_id,
                seat_no,
                cost,
                f.flight_no,
                f.status,
                f.aircraft_id,
                f.arrival_airport_code,
                f.arrival_date,
                f.departure_airport_code,
                f.departure_date
            FROM ticket
            JOIN flight f
                ON ticket.flight_id = f.id
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

    public List<TicketEntity> findByFilter(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        if (filter.getPassengerNo() != null) {
            conditions.add("passenger_no LIKE ?");
            parameters.add("%" + filter.getPassengerNo() + "%");
        }
        if (filter.getPassengerName() != null) {
            conditions.add("passenger_name = ?");
            parameters.add(filter.getPassengerName());
        }
        if (filter.getFlightId() != null) {
            conditions.add("flight_id = ?");
            parameters.add(filter.getFlightId());
        }
        if (filter.getSeatNo() != null) {
            conditions.add("seat_no LIKE ?");
            parameters.add("%" + filter.getSeatNo() + "%");
        }
        if (filter.getCost() != null) {
            conditions.add("cost = ?");
            parameters.add(filter.getCost());
        }
        parameters.add(filter.getLimit());
        parameters.add(filter.getOffset());

        // Build the SQL query
        StringBuilder queryBuilder = new StringBuilder(FIND_ALL);

        if (!conditions.isEmpty()) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", conditions));
        }
        queryBuilder.append(" LIMIT ? OFFSET ?");

        String query = queryBuilder.toString();
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(preparedStatement); // print final query
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TicketEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildTicket(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("Failed to find tickets with filter", e);
        }
    }

    public List<TicketEntity> findAll() {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TicketEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                TicketEntity ticket = buildTicket(resultSet);
                result.add(ticket);
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException("Failed to find all tickets", e);
        }
    }

    public Optional<TicketEntity> findById(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            TicketEntity ticketEntity = null;
            if (resultSet.next()) {
                ticketEntity = buildTicket(resultSet);
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
            preparedStatement.setLong(3, ticketEntity.getFlight().getId());
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
            preparedStatement.setLong(3, ticketEntity.getFlight().getId());
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

    private TicketEntity buildTicket(ResultSet resultSet) throws SQLException {
        FlightEntity flightEntity = new FlightEntity()
                .setId(resultSet.getLong("flight_id"))
                .setFlightNo(resultSet.getString("flight_no"))
                .setDepartureDate(resultSet.getTimestamp("departure_date").toLocalDateTime())
                .setDepartureAirportCode(resultSet.getString("departure_airport_code"))
                .setArrivalDate(resultSet.getTimestamp("arrival_date").toLocalDateTime())
                .setArrivalAirportCode(resultSet.getString("arrival_airport_code"))
                .setAircraftId(resultSet.getInt("aircraft_id"))
                .setStatus(resultSet.getString("status"));
        return new TicketEntity()
                .setId(resultSet.getLong("id"))
                .setPassengerNo(resultSet.getString("passenger_no"))
                .setPassengerName(resultSet.getString("passenger_name"))
                .setFlight(flightEntity)
                .setSeatNo(resultSet.getString("seat_no"))
                .setCost(resultSet.getBigDecimal("cost"));
    }

}

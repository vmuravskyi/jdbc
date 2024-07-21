package jdbc.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class TicketEntity {

    private Long id;
    private String passengerNo;
    private String passengerName;
    private FlightEntity flight;
    private String seatNo;
    private BigDecimal cost;

    public TicketEntity() {
        // default constructor
    }

    public TicketEntity(Long id, String passengerNo, String passengerName, FlightEntity flight, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passengerNo = passengerNo;
        this.passengerName = passengerName;
        this.flight = flight;
        this.seatNo = seatNo;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public TicketEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPassengerNo() {
        return passengerNo;
    }

    public TicketEntity setPassengerNo(String passengerNo) {
        this.passengerNo = passengerNo;
        return this;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public TicketEntity setPassengerName(String passengerName) {
        this.passengerName = passengerName;
        return this;
    }

    public FlightEntity getFlight() {
        return flight;
    }

    public TicketEntity setFlight(FlightEntity flight) {
        this.flight = flight;
        return this;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public TicketEntity setSeatNo(String seatNo) {
        this.seatNo = seatNo;
        return this;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public TicketEntity setCost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketEntity that = (TicketEntity) o;

        return Objects.equals(id, that.id) &&
               Objects.equals(passengerNo, that.passengerNo) &&
               Objects.equals(passengerName, that.passengerName) &&
               Objects.equals(flight, that.flight) &&
               Objects.equals(seatNo, that.seatNo) &&
               Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                passengerNo,
                passengerName,
                flight,
                seatNo,
                cost);
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
               "id=" + id +
               ", passengerNo='" + passengerNo + '\'' +
               ", passengerName='" + passengerName + '\'' +
               ", flight=" + flight +
               ", seatNo='" + seatNo + '\'' +
               ", cost=" + cost +
               '}';
    }

}

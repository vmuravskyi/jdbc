package jdbc.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class TicketEntity {

    private Long id;
    private String passengerNo;
    private String passengerName;
    private Long flightId;
    private String seatNo;
    private BigDecimal cost;

    public TicketEntity() {
        // default constructor
    }

    public TicketEntity(Long id, String passengerNo, String passengerName, Long flightId, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passengerNo = passengerNo;
        this.passengerName = passengerName;
        this.flightId = flightId;
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

    public Long getFlightId() {
        return flightId;
    }

    public TicketEntity setFlightId(Long flightId) {
        this.flightId = flightId;
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

        return Objects.equals(id, that.id) && Objects.equals(passengerNo, that.passengerNo) && Objects.equals(passengerName, that.passengerName) && Objects.equals(flightId, that.flightId) && Objects.equals(seatNo, that.seatNo) && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passengerNo, passengerName, flightId, seatNo, cost);
    }

    @Override
    public String toString() {
        return "TicketEntity{" + "id=" + id + ", passenger_no='" + passengerNo + '\'' + ", passenger_name='" + passengerName + '\'' + ", flight_id=" + flightId + ", seat_no='" + seatNo + '\'' + ", cost=" + cost + '}';
    }

}

package jdbc.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightEntity {

    private Long id;
    private String flightNo;
    private LocalDateTime departureDate;
    private String departureAirportCode;
    private LocalDateTime arrivalDate;
    private String arrivalAirportCode;
    private Integer aircraftId;
    private String status;

    public FlightEntity() {
        // default constructor
    }

    public FlightEntity(Long id,
                        String flightNo,
                        LocalDateTime departureDate,
                        String departureAirportCode,
                        LocalDateTime arrivalDate,
                        String arrivalAirportCode,
                        Integer aircraftId,
                        String status) {
        this.id = id;
        this.flightNo = flightNo;
        this.departureDate = departureDate;
        this.departureAirportCode = departureAirportCode;
        this.arrivalDate = arrivalDate;
        this.arrivalAirportCode = arrivalAirportCode;
        this.aircraftId = aircraftId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public FlightEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public FlightEntity setFlightNo(String flightNo) {
        this.flightNo = flightNo;
        return this;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public FlightEntity setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public FlightEntity setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
        return this;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public FlightEntity setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public FlightEntity setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
        return this;
    }

    public Integer getAircraftId() {
        return aircraftId;
    }

    public FlightEntity setAircraftId(Integer aircraftId) {
        this.aircraftId = aircraftId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FlightEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(flightNo, that.flightNo) &&
               Objects.equals(departureDate, that.departureDate) &&
               Objects.equals(departureAirportCode, that.departureAirportCode) &&
               Objects.equals(arrivalDate, that.arrivalDate) &&
               Objects.equals(arrivalAirportCode, that.arrivalAirportCode) &&
               Objects.equals(aircraftId, that.aircraftId) &&
               Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                flightNo,
                departureDate,
                departureAirportCode,
                arrivalDate,
                arrivalAirportCode,
                aircraftId,
                status);
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
               "id=" + id +
               ", flightNo='" + flightNo + '\'' +
               ", departureDate=" + departureDate +
               ", departureAirportCode='" + departureAirportCode + '\'' +
               ", arrivalDate=" + arrivalDate +
               ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
               ", aircraftId=" + aircraftId +
               ", status='" + status + '\'' +
               '}';
    }

}

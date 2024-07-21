package jdbc.dao;

import java.math.BigDecimal;

public class TicketFilter {

    private final int limit;
    private final int offset;
    private final String passengerNo;
    private final String passengerName;
    private final Long flightId;
    private final String seatNo;
    private final BigDecimal cost;

    private TicketFilter(Builder builder) {
        this.limit = builder.limit;
        this.offset = builder.offset;
        this.passengerNo = builder.passengerNo;
        this.passengerName = builder.passengerName;
        this.flightId = builder.flightId;
        this.seatNo = builder.seatNo;
        this.cost = builder.cost;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public String getPassengerNo() {
        return passengerNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public Long getFlightId() {
        return flightId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public static class Builder {

        private int limit;
        private int offset;
        private String passengerNo;
        private String passengerName;
        private Long flightId;
        private String seatNo;
        private BigDecimal cost;

        private Builder() {
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder passengerNo(String passengerNo) {
            this.passengerNo = passengerNo;
            return this;
        }

        public Builder passengerName(String passengerName) {
            this.passengerName = passengerName;
            return this;
        }

        public Builder flightId(Long flightId) {
            this.flightId = flightId;
            return this;
        }

        public Builder seatNo(String seatNo) {
            this.seatNo = seatNo;
            return this;
        }

        public Builder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public TicketFilter build() {
            return new TicketFilter(this);
        }
    }

}


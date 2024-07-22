package jdbc;

import jdbc.dao.FlightDao;
import jdbc.dao.TicketDao;
import jdbc.dao.TicketFilter;
import jdbc.entity.FlightEntity;
import jdbc.entity.TicketEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {

//        Optional<FlightEntity> flightEntity = FlightDao.getInstance().findById(1L);
//        System.out.println(flightEntity);

        Optional<TicketEntity> ticket = TicketDao.getInstance().findById(78L);
        System.out.println(ticket);

//        testFilter();
//        crud();

    }

    private static void testFilter() {
        TicketFilter ticketFilter = TicketFilter.builder()
                .passengerNo("SS988D")
                .passengerName("Michael Johnson")
                .flightId(1L)
                .seatNo("B2")
                .cost(BigDecimal.valueOf(175.00))
                .limit(10)
                .offset(0)
                .build();

        TicketDao ticketDao = TicketDao.getInstance();

        List<TicketEntity> all = ticketDao.findByFilter(ticketFilter);
        System.out.println(all.size());
        System.out.println(all);
    }

    private static void crud() {
        TicketDao ticketDao = TicketDao.getInstance();

        // create and save
        TicketEntity ticket = new TicketEntity()
                .setPassengerNo("JHY789")
                .setPassengerName("Freddy Mercury")
                .setFlight(null)
                .setSeatNo("F4")
                .setCost(new BigDecimal("123.99"));

        TicketEntity savedTicket = ticketDao.save(ticket);
        System.out.println("Created and saved ticket:\n" + savedTicket + "\n");

        // get
        Optional<TicketEntity> optionalTicket = ticketDao.findById(savedTicket.getId());
        System.out.println("Found by id ticket:\n" + optionalTicket + "\n");

        // update
        optionalTicket.ifPresent(ticketEntity -> {
            ticketEntity.setCost(BigDecimal.valueOf(185.55));
            ticketDao.update(ticketEntity);
        });
        Optional<TicketEntity> updatedTicket = ticketDao.findById(savedTicket.getId());
        System.out.println("Updated ticket:\n" + updatedTicket + "\n");

        // delete
        boolean deleted = ticketDao.delete(savedTicket.getId());
        System.out.println("Deleted ticket:\n" + deleted + "\n");

        // find all
        List<TicketEntity> allTickets = ticketDao.findAll();
        System.out.println("Total number of tickets: " + allTickets.size());
        for (TicketEntity t : allTickets) {
            System.out.println(t);
        }
    }

}

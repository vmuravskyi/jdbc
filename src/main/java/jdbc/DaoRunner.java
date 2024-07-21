package jdbc;

import jdbc.dao.TicketDao;
import jdbc.entity.TicketEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DaoRunner {

    public static void main(String[] args) {
        TicketDao ticketDao = TicketDao.getInstance();

        // create and save
        TicketEntity ticket = new TicketEntity()
                .setPassengerNo("JHY789")
                .setPassengerName("Freddy Mercury")
                .setFlightId(3L)
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

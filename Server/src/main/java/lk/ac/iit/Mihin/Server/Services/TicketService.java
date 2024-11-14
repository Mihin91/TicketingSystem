package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.TicketPool.TicketPool;
import org.springframework.stereotype.Service;


@Service
public class TicketService {
    private final TicketPool ticketPool;

    public TicketService() {

        this.ticketPool = new TicketPool(100);
    }


    public String addTickets(int ticketCount) {
        if (ticketPool.hasCapacity()) {
            ticketPool.addTickets(ticketCount);
            return "Tickets added successfully";
        } else {
            return "Max capacity reached. Cannot add more tickets.";
        }
    }

    public String purchaseTicket(int customerId) {
        if (ticketPool.hasTickets()) {
            ticketPool.removeTicket();
            return "Customer " + customerId + " purchased a ticket.";
        } else {
            return "No tickets available for purchase.";
        }
    }

    public int getTicketCount() {
        return ticketPool.getCurrentTickets();
    }
}

package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.TicketPool.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketPool ticketPool;

    @Autowired
    public TicketService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public String addTickets(int ticketCount, Long vendorId) {
        if (ticketPool.hasCapacity()) {
            return ticketPool.addTickets(ticketCount, vendorId);  // Pass vendorId here
        } else {
            return "Max capacity reached. Cannot add more tickets.";
        }
    }

    public String purchaseTicket(int customerId) {
        if (ticketPool.hasTickets()) {
            ticketPool.removeTicket((long) customerId);  // Assuming customerId is of type Long
            return "Customer " + customerId + " purchased a ticket.";
        } else {
            return "No tickets available for purchase.";
        }
    }

    public int getTicketCount() {
        return ticketPool.getCurrentTickets();
    }
}

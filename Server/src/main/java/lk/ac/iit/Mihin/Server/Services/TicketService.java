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

    // Method to add tickets, checks capacity first
    public String addTickets(int ticketCount, Long vendorId) {
        if (ticketPool.hasCapacity()) {
            return ticketPool.addTickets(ticketCount, vendorId);  // Passing vendorId to addTickets
        } else {
            return "Max capacity reached. Cannot add more tickets.";
        }
    }

    // Method for a customer to purchase a ticket
    public String purchaseTicket(Long customerId) { // Changed customerId type to Long for consistency
        if (ticketPool.hasTickets()) {
            ticketPool.removeTicket(customerId);  // Assuming removeTicket takes Long
            return "Customer " + customerId + " purchased a ticket.";
        } else {
            return "No tickets available for purchase.";
        }
    }

    // Method to get the current ticket count
    public int getTicketCount() {
        return ticketPool.getCurrentTickets();
    }
}

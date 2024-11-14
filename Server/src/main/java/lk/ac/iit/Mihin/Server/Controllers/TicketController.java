package lk.ac.iit.Mihin.Server.Controllers;


import lk.ac.iit.Mihin.Server.TicketPool.TicketPool;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketPool ticketPool;

    public TicketController() {
        // Initialize TicketPool with max capacity
        this.ticketPool = new TicketPool(100);  // Set max capacity to 100
    }

    // Endpoint to get current ticket count
    @GetMapping("/count")
    public int getTicketCount() {
        return ticketPool.getCurrentTickets();
    }

    // Endpoint to add tickets to the pool (e.g., called by vendor)
    @PostMapping("/add")
    public String addTickets(@RequestParam int ticketCount) {
        if(ticketPool.hasCapacity()) {
            ticketPool.addTickets(ticketCount);
            return "Tickets added successfully";
        } else {
            return "Max capacity reached. Cannot add more tickets.";
        }
    }

    // Endpoint for customers to purchase tickets
    @PostMapping("/purchase")
    public String purchaseTicket(@RequestParam int customerId) {
        if(ticketPool.hasTickets()) {
            ticketPool.removeTicket();
            return "Customer " + customerId + " purchased a ticket.";
        } else {
            return "No tickets available for purchase.";
        }
    }
}

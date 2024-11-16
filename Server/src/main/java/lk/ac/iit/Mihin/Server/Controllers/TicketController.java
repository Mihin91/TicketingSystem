package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/count")
    public int getTicketCount() {
        return ticketService.getTicketCount();
    }

    @PostMapping("/add")
    public String addTickets(@RequestParam int ticketCount, @RequestParam Long vendorId) {
        return ticketService.addTickets(ticketCount, vendorId);
    }

    @PostMapping("/purchase")
    public String purchaseTicket(@RequestParam int customerId) {
        return ticketService.purchaseTicket(customerId);
    }
}

package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketPoolController {

    private final TicketService ticketService;

    @Autowired
    public TicketPoolController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> getTicketCount() {
        int ticketCount = ticketService.getTicketCount();
        return new ResponseEntity<>(ticketCount, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<String> addTickets(@RequestParam int ticketCount, @RequestParam Long vendorId) {
        String response = ticketService.addTickets(ticketCount, vendorId);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Created status for successful resource creation
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTicket(@RequestParam Long customerId) {  // Changed to Long for consistency
        String response = ticketService.purchaseTicket(customerId);
        if(response.contains("purchased")) {
            return new ResponseEntity<>(response, HttpStatus.OK);  // Success
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // Failure (no tickets available)
        }
    }
}

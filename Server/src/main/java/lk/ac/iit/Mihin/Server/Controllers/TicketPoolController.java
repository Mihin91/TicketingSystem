package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.TicketDTO;
import lk.ac.iit.Mihin.Server.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticketpool")
public class TicketPoolController {

    private final TicketService ticketPoolService;

    @Autowired
    public TicketPoolController(TicketService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    // Create or update TicketPool
    @PostMapping
    public ResponseEntity<TicketDTO> createOrUpdateTicketPool(@RequestBody TicketDTO ticketPoolDTO) {
        TicketDTO savedTicketPool = ticketPoolService.saveTicketPool(ticketPoolDTO);
        return ResponseEntity.ok(savedTicketPool);
    }

    // Get TicketPool by ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketPool(@PathVariable Long id) {
        Optional<TicketDTO> ticketPool = ticketPoolService.getTicketPool(id);
        return ticketPool.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all TicketPools
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTicketPools() {
        List<TicketDTO> ticketPools = ticketPoolService.getAllTicketPools();
        return ResponseEntity.ok(ticketPools);
    }

    // Delete TicketPool by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketPool(@PathVariable Long id) {
        ticketPoolService.deleteTicketPool(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

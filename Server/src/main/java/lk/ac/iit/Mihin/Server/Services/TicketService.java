package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.DTO.TicketDTO;
import lk.ac.iit.Mihin.Server.Model.TicketPool;
import lk.ac.iit.Mihin.Server.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Method to save or update TicketPool
    public TicketDTO saveTicketPool(TicketDTO ticketPoolDTO) {
        TicketPool ticketPool = new TicketPool(ticketPoolDTO.getMaxCapacity());
        ticketPool.setMaxCapacity(ticketPoolDTO.getMaxCapacity());
        ticketPool = ticketRepository.save(ticketPool);

        // Return DTO with the updated ticket pool details
        return new TicketDTO(ticketPool.getId(), ticketPool.getMaxCapacity(),
                ticketPool.getCurrentTickets(), ticketPool.getRemainingTickets());
    }

    // Method to get TicketPool by ID
    public Optional<TicketDTO> getTicketPool(Long id) {
        Optional<TicketPool> ticketPool = ticketRepository.findById(id);
        return ticketPool.map(tp -> new TicketDTO(tp.getId(), tp.getMaxCapacity(),
                tp.getCurrentTickets(), tp.getRemainingTickets()));
    }

    // Method to delete TicketPool by ID
    public void deleteTicketPool(Long id) {
        ticketRepository.deleteById(id);
    }

    // Method to get all TicketPools
    public List<TicketDTO> getAllTicketPools() {
        return ticketRepository.findAll().stream()
                .map(tp -> new TicketDTO(tp.getId(), tp.getMaxCapacity(), tp.getCurrentTickets(), tp.getRemainingTickets()))
                .collect(Collectors.toList());
    }
}

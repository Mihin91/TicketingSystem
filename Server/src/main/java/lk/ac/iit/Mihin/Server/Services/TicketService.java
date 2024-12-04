package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.DTO.TicketStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to handle ticket-related operations.
 */
@Service
public class TicketService {

    private final TicketPoolService ticketPoolService;

    /**
     * Constructor for TicketService.
     *
     * @param ticketPoolService The service managing the ticket pool.
     */
    @Autowired
    public TicketService(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    /**
     * Retrieves the current status of the ticket pool.
     *
     * @return A DTO containing the current ticket status.
     */
    public TicketStatusDTO getTicketStatus() {
        TicketStatusDTO statusDTO = new TicketStatusDTO();
        statusDTO.setCurrentTickets(ticketPoolService.getCurrentTickets());
        statusDTO.setTotalTicketsReleased(ticketPoolService.getTotalTicketsReleased());
        statusDTO.setTotalTicketsPurchased(ticketPoolService.getTotalTicketsPurchased());
        return statusDTO;
    }
}

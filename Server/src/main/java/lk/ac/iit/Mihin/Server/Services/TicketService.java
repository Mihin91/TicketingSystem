package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.DTO.TicketStatusDTO;
import lk.ac.iit.Mihin.Server.Model.TicketPool;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketPool ticketPool;

    public TicketService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public TicketStatusDTO getTicketStatus() {
        TicketStatusDTO statusDTO = new TicketStatusDTO();
        statusDTO.setCurrentTickets(ticketPool.getCurrentTickets());
        statusDTO.setTotalTicketsReleased(ticketPool.getTotalTicketsReleased());
        statusDTO.setTotalTicketsPurchased(ticketPool.getTotalTicketsPurchased());
        return statusDTO;
    }

    // Additional methods if necessary
}

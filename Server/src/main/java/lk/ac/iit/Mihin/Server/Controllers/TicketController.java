package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.TicketStatusDTO;
import lk.ac.iit.Mihin.Server.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TicketController(SimpMessagingTemplate messagingTemplate, TicketService ticketService) {
        this.messagingTemplate = messagingTemplate;
        this.ticketService = ticketService;
    }

    @MessageMapping("/tickets/status")
    public void sendTicketStatus() {
        TicketStatusDTO statusDTO = ticketService.getTicketStatus();
        messagingTemplate.convertAndSend("/topic/tickets/status", statusDTO);
    }
}

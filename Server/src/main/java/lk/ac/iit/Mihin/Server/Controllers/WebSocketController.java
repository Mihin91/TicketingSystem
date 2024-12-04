package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Model.Tickets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendTicketUpdate")
    public void sendTicketUpdate(Ticket ticket) {
        messagingTemplate.convertAndSend("/topic/tickets", ticket);
    }

    @MessageMapping("/sendLog")
    public void sendLog(String log) {
        messagingTemplate.convertAndSend("/topic/logs", log);
    }
}

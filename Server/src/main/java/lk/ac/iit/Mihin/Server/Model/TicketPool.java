package lk.ac.iit.Mihin.Server.Model;

import lk.ac.iit.Mihin.Server.DTO.TicketStatusDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class TicketPool {
    private int maxCapacity;
    private final Queue<String> tickets;
    private int totalTicketsReleased = 0;
    private int totalTicketsPurchased = 0;

    private final SimpMessagingTemplate messagingTemplate;

    public TicketPool(SimpMessagingTemplate messagingTemplate) {
        this.tickets = new LinkedList<>();
        this.messagingTemplate = messagingTemplate;
        // maxCapacity should be set via a setter before use
    }

    public synchronized void addTicket(String ticket) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            System.out.println("[TicketPool] Pool is full. Vendor waiting...");
            wait();
        }
        tickets.add(ticket);
        totalTicketsReleased++;
        System.out.println("[TicketPool] Ticket added: " + ticket + " | Pool size: " + tickets.size());
        notifyAll();
        broadcastStatus();
    }

    public synchronized String removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            System.out.println("[TicketPool] No tickets available. Customer waiting...");
            wait();
        }
        String ticket = tickets.poll();
        totalTicketsPurchased++;
        System.out.println("[TicketPool] Ticket purchased: " + ticket + " | Pool size: " + tickets.size());
        notifyAll();
        broadcastStatus();
        return ticket;
    }

    private void broadcastStatus() {
        TicketStatusDTO statusDTO = new TicketStatusDTO();
        statusDTO.setCurrentTickets(getCurrentTickets());
        statusDTO.setTotalTicketsReleased(getTotalTicketsReleased());
        statusDTO.setTotalTicketsPurchased(getTotalTicketsPurchased());
        messagingTemplate.convertAndSend("/topic/tickets/status", statusDTO);
    }

    public synchronized int getCurrentTickets() {
        return tickets.size();
    }

    public synchronized int getRemainingCapacity() {
        return maxCapacity - tickets.size();
    }

    public synchronized int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public synchronized int getTotalTicketsPurchased() {
        return totalTicketsPurchased;
    }

    // Setter for maxCapacity
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Existing methods...
}

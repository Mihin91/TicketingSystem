// src/main/java/lk/ac/iit/Mihin/Server/Services/TicketPoolService.java
package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.DTO.TicketStatusDTO;
import lk.ac.iit.Mihin.Server.Events.TicketsSoldOutEvent;
import lk.ac.iit.Mihin.Server.Model.Ticket;
import lk.ac.iit.Mihin.Server.Repositories.CustomerRepository;
import lk.ac.iit.Mihin.Server.Repositories.TicketRepository;
import lk.ac.iit.Mihin.Server.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class TicketPoolService {

    private int maxCapacity;
    private int totalTickets;
    private int totalTicketsReleased = 0;
    private int totalTicketsPurchased = 0;
    private boolean isClosed = false;

    private final Queue<Ticket> ticketsQueue;
    private final TicketRepository ticketRepository;
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TicketPoolService(TicketRepository ticketRepository,
                             VendorRepository vendorRepository,
                             CustomerRepository customerRepository,
                             SimpMessagingTemplate messagingTemplate) {
        this.ticketsQueue = new LinkedList<>();
        this.ticketRepository = ticketRepository;
        this.vendorRepository = vendorRepository;
        this.customerRepository = customerRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Initializes the ticket pool with the given configuration.
     *
     * @param maxCapacity  Maximum capacity of the pool.
     * @param totalTickets Total tickets to be released.
     */
    public synchronized void initializePool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.totalTicketsReleased = 0;
        this.totalTicketsPurchased = 0;
        this.isClosed = false;
        this.ticketsQueue.clear();
        ticketRepository.deleteAll(); // Clear existing tickets
        notifyAll();
        broadcastStatus();
    }

    /**
     * Adds a ticket to the pool associated with a vendor.
     *
     * @param vendorId ID of the vendor releasing the ticket.
     * @return The added ticket or null if the pool is full or closed.
     */
    public synchronized Ticket addTicket(int vendorId) throws InterruptedException {
        while (ticketsQueue.size() >= maxCapacity && !isClosed) {
            System.out.println("[TicketPool] Pool is full. Vendor " + vendorId + " waiting...");
            wait();
        }

        if (totalTicketsReleased >= totalTickets) {
            isClosed = true;
            notifyAll();
            return null;
        }

        if (ticketsQueue.size() < maxCapacity && totalTicketsReleased < totalTickets) {
            Ticket ticket = new Ticket();
            ticket.setStatus(Ticket.TicketStatus.AVAILABLE);
            // Associate vendor
            ticket.setVendor(vendorRepository.findById(vendorId).orElse(null));
            ticketRepository.save(ticket);
            ticketsQueue.add(ticket);
            totalTicketsReleased++;

            System.out.println("[TicketPool] Ticket added: " + ticket.getId() + " | Pool size: " + ticketsQueue.size());
            logService.addLog("[Vendor] " + vendorId + " released: Ticket-" + ticket.getId() + " | Total tickets released: " + totalTicketsReleased + "\nAvailable tickets: " + getCurrentTickets());
            notifyAll();
            broadcastStatus();
            return ticket;
        } else {
            return null;
        }
    }

    /**
     * Removes (purchases) a ticket from the pool associated with a customer.
     *
     * @param customerId ID of the customer purchasing the ticket.
     * @return The purchased ticket or null if the pool is empty or closed.
     */
    public synchronized Ticket removeTicket(int customerId) throws InterruptedException {
        while (ticketsQueue.isEmpty()) {
            if (isClosed && totalTicketsPurchased >= totalTickets) {
                notifyAll();
                return null;
            }
            wait();
        }

        Ticket ticket = ticketsQueue.poll();
        if (ticket != null) {
            ticket.setStatus(Ticket.TicketStatus.PURCHASED);
            ticket.setCustomer(customerRepository.findById(customerId).orElse(null));
            ticketRepository.save(ticket);
            totalTicketsPurchased++;

            // Stop the system if all tickets are sold
            if (totalTicketsPurchased >= totalTickets) {
                isClosed = true;
                notifyAll();
            }

            broadcastStatus();
        }
        return ticket;
    }


    /**
     * Broadcasts the current ticket status to subscribers.
     */
    private void broadcastStatus() {
        TicketStatusDTO statusDTO = new TicketStatusDTO();
        statusDTO.setCurrentTickets(getCurrentTickets());
        statusDTO.setTotalTicketsReleased(getTotalTicketsReleased());
        statusDTO.setTotalTicketsPurchased(getTotalTicketsPurchased());
        messagingTemplate.convertAndSend("/topic/tickets/status", statusDTO);
    }

    // Getters for status
    public synchronized int getCurrentTickets() {
        return ticketsQueue.size();
    }

    public synchronized int getRemainingCapacity() {
        return maxCapacity - ticketsQueue.size();
    }

    public synchronized int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public synchronized int getTotalTicketsPurchased() {
        return totalTicketsPurchased;
    }

    public synchronized boolean isClosed() {
        return isClosed;
    }

    /**
     * Resets the ticket pool.
     */
    public synchronized void resetPool() {
        ticketsQueue.clear();
        totalTicketsReleased = 0;
        totalTicketsPurchased = 0;
        isClosed = false;
        ticketRepository.deleteAll();
        System.out.println("[TicketPool] Pool has been reset.");
        logService.addLog("[System] Pool has been reset.");
        notifyAll();
        broadcastStatus();
    }

    // Injecting LogService for logging
    @Autowired
    private LogService logService;

    // Injecting Event Publisher to publish TicketsSoldOutEvent
    @Autowired
    private org.springframework.context.ApplicationEventPublisher eventPublisher;

    /**
     * Stops the simulation by publishing a TicketsSoldOutEvent.
     */
    private void stopSimulation() {
        TicketsSoldOutEvent event = new TicketsSoldOutEvent(this);
        eventPublisher.publishEvent(event);
    }
}

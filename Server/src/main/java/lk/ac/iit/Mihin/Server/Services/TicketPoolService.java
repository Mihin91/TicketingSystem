// src/main/java/lk/ac/iit/Mihin/Server/Services/TicketPoolService.java
package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.DTO.TicketStatusDTO;
import lk.ac.iit.Mihin.Server.Events.TicketsSoldOutEvent;
import lk.ac.iit.Mihin.Server.Model.Customer;
import lk.ac.iit.Mihin.Server.Model.Ticket;
import lk.ac.iit.Mihin.Server.Model.Vendor;
import lk.ac.iit.Mihin.Server.Repositories.CustomerRepository;
import lk.ac.iit.Mihin.Server.Repositories.TicketRepository;
import lk.ac.iit.Mihin.Server.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class TicketPoolService {

    private BlockingQueue<Ticket> ticketPool;
    private int totalTickets;
    private int totalTicketsReleased;
    private int totalTicketsPurchased;
    private int maxCapacity;
    private boolean poolClosed;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LogService logService;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Initializes the ticket pool if it hasn't been initialized yet.
     *
     * @param maxTicketCapacity Maximum capacity of the ticket pool.
     * @param totalTickets      Total number of tickets to be released.
     */
    public synchronized void initializePool(int maxTicketCapacity, int totalTickets) {
        if (this.ticketPool != null) {
            logService.addLog("[System] Ticket pool is already initialized.");
            return;
        }
        this.ticketPool = new LinkedBlockingQueue<>(maxTicketCapacity);
        this.maxCapacity = maxTicketCapacity;
        this.totalTickets = totalTickets;
        this.totalTicketsReleased = 0;
        this.totalTicketsPurchased = 0;
        this.poolClosed = false;
        logService.addLog("[System] Ticket pool initialized with capacity: " + maxTicketCapacity);
        sendTicketStatus(); // Notify frontend about the initial status
    }

    /**
     * Adds a ticket to the pool.
     *
     * @param vendorId ID of the vendor releasing the ticket.
     * @return The added ticket or null if the pool is full or closed.
     */
    public synchronized Ticket addTicket(int vendorId) {
        if (poolClosed || totalTicketsReleased >= totalTickets) {
            return null;
        }

        // Fetch Vendor from DB
        Vendor vendor = vendorRepository.findById(vendorId).orElse(null);
        if (vendor == null) {
            logService.addLog("[Error] Vendor with ID " + vendorId + " not found.");
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setVendor(vendor);
        ticket.setStatus(Ticket.TicketStatus.AVAILABLE);

        // Save ticket to DB to assign ID
        ticket = ticketRepository.save(ticket);

        try {
            ticketPool.put(ticket);
            totalTicketsReleased++;
            if (totalTicketsReleased >= totalTickets) {
                poolClosed = true;
            }
            sendTicketStatus();
            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * Removes a ticket from the pool for a customer.
     *
     * @param customerId ID of the customer purchasing the ticket.
     * @return The purchased ticket or null if the pool is empty.
     */
    public synchronized Ticket removeTicket(int customerId) {
        if (totalTicketsPurchased >= totalTickets) {
            return null;
        }

        Ticket ticket = ticketPool.poll();
        if (ticket != null) {
            // Fetch Customer from DB
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer == null) {
                logService.addLog("[Error] Customer with ID " + customerId + " not found.");
                return null;
            }

            ticket.setCustomer(customer);
            ticket.setStatus(Ticket.TicketStatus.PURCHASED);

            // Update the ticket in DB
            ticket = ticketRepository.save(ticket);

            totalTicketsPurchased++;
            sendTicketStatus();

            if (totalTicketsPurchased >= totalTickets) {
                eventPublisher.publishEvent(new TicketsSoldOutEvent(this));
            }

            return ticket;
        } else {
            return null;
        }
    }

    /**
     * Sends the current ticket status to the frontend via WebSocket.
     */
    private void sendTicketStatus() {
        TicketStatusDTO statusDTO = new TicketStatusDTO();
        statusDTO.setCurrentTickets(getCurrentTickets());
        statusDTO.setTotalTicketsReleased(getTotalTicketsReleased());
        statusDTO.setTotalTicketsPurchased(getTotalTicketsPurchased());
        messagingTemplate.convertAndSend("/topic/tickets/status", statusDTO);
    }

    // Getters for status
    public synchronized int getCurrentTickets() {
        return ticketPool.size();
    }

    public synchronized int getRemainingCapacity() {
        return maxCapacity - ticketPool.size();
    }

    public synchronized int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public synchronized int getTotalTicketsPurchased() {
        return totalTicketsPurchased;
    }

    public synchronized boolean isClosed() {
        return poolClosed;
    }

    /**
     * Resets the ticket pool, clearing all counts and the pool itself.
     */
    public synchronized void resetPool() {
        this.ticketPool.clear();
        this.totalTicketsReleased = 0;
        this.totalTicketsPurchased = 0;
        this.poolClosed = false;
        logService.addLog("[System] Ticket pool has been reset.");
        sendTicketStatus(); // Notify frontend about the reset status
    }

    /**
     * Checks if the ticket pool has been initialized.
     *
     * @return true if initialized, false otherwise.
     */
    public synchronized boolean isInitialized() {
        return this.ticketPool != null;
    }
}

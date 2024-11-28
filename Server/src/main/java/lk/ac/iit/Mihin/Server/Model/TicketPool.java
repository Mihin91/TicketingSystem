package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "ticket_pool")
public class TicketPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the entity

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @Transient // Excluded from persistence, as it is managed in-memory
    private final ConcurrentLinkedQueue<Integer> tickets = new ConcurrentLinkedQueue<>();

    @Transient // Excluded from persistence, as it is managed in-memory
    private final AtomicInteger currentSize = new AtomicInteger(0);

    // Default constructor required by JPA
    public TicketPool() {
        this.maxCapacity = 100; // Default maximum capacity
    }

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Add a ticket to the pool
    public boolean addTicket(int ticketId) {
        if (currentSize.get() < maxCapacity) {
            boolean added = tickets.offer(ticketId); // Non-blocking, thread-safe addition
            if (added) {
                currentSize.incrementAndGet(); // Atomically increase the size
                System.out.println("[TicketPool] Added ticket: " + ticketId);
            } else {
                System.out.println("[TicketPool] Failed to add ticket: " + ticketId);
            }
            return added;
        }
        System.out.println("[TicketPool] Cannot add ticket: " + ticketId + ". Pool is at maximum capacity.");
        return false;
    }

    // Remove a ticket from the pool
    public Integer removeTicket() {
        Integer ticketId = tickets.poll(); // Non-blocking, thread-safe removal
        if (ticketId != null) {
            currentSize.decrementAndGet(); // Atomically decrease the size
            System.out.println("[TicketPool] Removed ticket: " + ticketId);
        } else {
            System.out.println("[TicketPool] No tickets available to remove.");
        }
        return ticketId;
    }

    // Check if there are tickets in the pool
    public boolean hasTickets() {
        return !tickets.isEmpty();
    }

    // Check if there's capacity to add more tickets
    public boolean hasCapacity() {
        return currentSize.get() < maxCapacity;
    }

    // Get the current size of the ticket pool
    public int getCurrentTickets() {
        return currentSize.get();
    }

    // Get the remaining tickets
    public int getRemainingTickets() {
        return maxCapacity - currentSize.get();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
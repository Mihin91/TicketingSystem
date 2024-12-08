package lk.ac.iit.Mihin.CLI;

import java.util.*;

/**
 * The TicketPool class manages the pool of tickets in a thread-safe manner.
 * Vendors add tickets to the pool, and customers remove tickets from the pool.
 * It utilizes Collections.synchronizedList to ensure thread safety for individual operations
 * and synchronized blocks for compound actions to maintain data consistency.
 */
public class TicketPool {
    private int maxCapacity;
    private int totalTickets;
    private final List<String> tickets; // Synchronized list for thread-safe operations
    private int totalTicketsReleased = 0;
    private int totalTicketsPurchased = 0;
    private int nextTicketId = 1;
    private boolean isClosed = false;
    private final Map<Integer, Integer> vendorTicketsReleased = new HashMap<>();

    /**
     * Initializes the TicketPool with maximum capacity and total tickets.
     *
     * @param maxCapacity  Maximum number of tickets the pool can hold at any time.
     * @param totalTickets Total number of tickets to be released by all vendors.
     */
    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        // Initialize the tickets list as a synchronized list to ensure thread safety
        this.tickets = Collections.synchronizedList(new LinkedList<>());
    }

    /**
     * Retrieves the total number of tickets configured.
     *
     * @return Total number of tickets.
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Retrieves the maximum capacity of the ticket pool.
     *
     * @return Maximum ticket pool capacity.
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Retrieves the total number of tickets released so far.
     *
     * @return Total tickets released.
     */
    public int getTotalTicketsReleased() {
        synchronized (tickets) {
            return totalTicketsReleased;
        }
    }

    /**
     * Retrieves the total number of tickets purchased so far.
     *
     * @return Total tickets purchased.
     */
    public int getTotalTicketsPurchased() {
        synchronized (tickets) {
            return totalTicketsPurchased;
        }
    }

    /**
     * Checks if the ticket pool is closed for adding more tickets.
     *
     * @return True if the pool is closed, false otherwise.
     */
    public boolean isClosed() {
        synchronized (tickets) {
            return isClosed;
        }
    }

    /**
     * Retrieves the current number of tickets available in the pool.
     *
     * @return Number of tickets currently in the pool.
     */
    public int getCurrentTickets() {
        synchronized (tickets) {
            return tickets.size();
        }
    }

    /**
     * Retrieves the remaining capacity of the ticket pool.
     *
     * @return Number of additional tickets that can be added to the pool.
     */
    public int getRemainingTickets() {
        synchronized (tickets) {
            return maxCapacity - tickets.size();
        }
    }

    /**
     * Adds a ticket to the pool if capacity and total tickets limit allow.
     *
     * @param vendorId The ID of the vendor adding the ticket.
     * @return The ticket string if added successfully, null otherwise.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public String addTicket(int vendorId) throws InterruptedException {
        synchronized (tickets) {
            // Wait until there's space in the pool or all tickets have been released
            while (tickets.size() >= maxCapacity) {
                if (totalTicketsReleased >= totalTickets) {
                    isClosed = true;
                    tickets.notifyAll(); // Notify waiting threads about pool closure
                    return null; // No more tickets can be added
                }
                tickets.wait(); // Wait for space to become available
            }

            // Check again if all tickets have been released
            if (totalTicketsReleased >= totalTickets) {
                isClosed = true;
                tickets.notifyAll();
                return null;
            }

            String ticket = "Ticket-" + nextTicketId++;
            tickets.add(ticket);
            totalTicketsReleased++;

            vendorTicketsReleased.put(vendorId, vendorTicketsReleased.getOrDefault(vendorId, 0) + 1);

            Logger.log("[TicketPool] Ticket added: " + ticket + " | Pool size: " + tickets.size());

            tickets.notifyAll();
            return ticket;
        }
    }

    /**
     * Removes a ticket from the pool if available.
     *
     * @return The ticket string if removed successfully, null otherwise.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public String removeTicket() throws InterruptedException {
        synchronized (tickets) {
            while (tickets.isEmpty()) {
                if (isClosed && totalTicketsPurchased >= totalTickets) {
                    return null; // No more tickets will be available
                }
                Logger.log("[TicketPool] No tickets available. Customer waiting...");
                tickets.wait();
            }

            String ticket;
            if (tickets instanceof LinkedList) {
                ticket = ((LinkedList<String>) tickets).poll();
            } else {
                ticket = tickets.remove(0);
            }

            totalTicketsPurchased++;
            Logger.log("[TicketPool] Ticket purchased: " + ticket + " | Pool size: " + tickets.size());
            tickets.notifyAll();
            return ticket;
        }
    }

    /**
     * Retrieves the total number of tickets released by a specific vendor.
     *
     * @param vendorId The ID of the vendor.
     * @return Total tickets released by the vendor.
     */
    public int getVendorTicketsReleased(int vendorId) {
        synchronized (tickets) {
            return vendorTicketsReleased.getOrDefault(vendorId, 0);
        }
    }

    /**
     * Retrieves all vendor IDs that have released tickets.
     *
     * @return A set of vendor IDs.
     */
    public Set<Integer> getAllVendorIds() {
        synchronized (tickets) {
            return new HashSet<>(vendorTicketsReleased.keySet());
        }
    }

    /**
     * Resets the TicketPool to its initial state with new configuration parameters.
     *
     * @param newMaxCapacity  New maximum capacity of the ticket pool.
     * @param newTotalTickets New total number of tickets to be released.
     */
    public synchronized void reset(int newMaxCapacity, int newTotalTickets) {
        this.maxCapacity = newMaxCapacity;
        this.totalTickets = newTotalTickets;
        
        synchronized (tickets) {
            tickets.clear();
            totalTicketsReleased = 0;
            totalTicketsPurchased = 0;
            nextTicketId = 1;
            isClosed = false;
            vendorTicketsReleased.clear();
        }

        Logger.log("[TicketPool] Ticket pool has been reset with new configuration: " +
                "Max Capacity = " + newMaxCapacity + ", Total Tickets = " + newTotalTickets);
    }
}

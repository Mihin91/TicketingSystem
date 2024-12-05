package lk.ac.iit.Mihin.CLI;

import java.util.*;

public class TicketPool {
    private final int maxCapacity;
    private final int totalTickets;
    private final Queue<String> tickets;
    private int totalTicketsReleased = 0;
    private int totalTicketsPurchased = 0;
    private int nextTicketId = 1;
    private boolean isClosed = false;
    private final Map<Integer, Integer> vendorTicketsReleased = new HashMap<>();

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.tickets = new LinkedList<>();
    }

    // Getter for totalTickets
    public synchronized int getTotalTickets() {
        return totalTickets;
    }

    // Getter for maxCapacity
    public synchronized int getMaxCapacity() {
        return maxCapacity;
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

    public synchronized int getCurrentTickets() {
        return tickets.size();
    }

    public synchronized int getRemainingTickets() {
        return maxCapacity - tickets.size();
    }

    /**
     * Adds a ticket to the pool if possible.
     *
     * @param vendorId The ID of the vendor adding the ticket.
     * @return The ticket string if added successfully, null otherwise.
     */
    public synchronized String addTicket(int vendorId) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            if (totalTicketsReleased >= totalTickets) {
                isClosed = true;
                notifyAll();
                return null; // No more tickets can be added
            }
            wait();
        }

        if (totalTicketsReleased >= totalTickets) {
            isClosed = true;
            notifyAll();
            return null; // No more tickets can be added
        }

        String ticket = "Ticket-" + nextTicketId++;
        tickets.add(ticket);
        totalTicketsReleased++;

        // Update vendor's released ticket count
        vendorTicketsReleased.put(vendorId, vendorTicketsReleased.getOrDefault(vendorId, 0) + 1);

        Logger.log("[TicketPool] Ticket added: " + ticket + " | Pool size: " + tickets.size());
        notifyAll(); // Notify all waiting threads that the pool state has changed
        return ticket;
    }

    /**
     * Removes a ticket from the pool if available.
     *
     * @return The ticket string if removed successfully, null otherwise.
     */
    public synchronized String removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            if (isClosed && totalTicketsPurchased >= totalTickets) {
                return null; // No more tickets will be available
            }
            Logger.log("[TicketPool] No tickets available. Customer waiting...");
            wait();
        }

        String ticket = tickets.poll();
        totalTicketsPurchased++;
        Logger.log("[TicketPool] Ticket purchased: " + ticket + " | Pool size: " + tickets.size());
        notifyAll(); // Notify all waiting threads that the pool state has changed
        return ticket;
    }

    /**
     * Gets the total tickets released by a specific vendor.
     *
     * @param vendorId The ID of the vendor.
     * @return Total tickets released by the vendor.
     */
    public synchronized int getVendorTicketsReleased(int vendorId) {
        return vendorTicketsReleased.getOrDefault(vendorId, 0);
    }

    /**
     * Retrieves all vendor IDs that have released tickets.
     *
     * @return A set of vendor IDs.
     */
    public synchronized Set<Integer> getAllVendorIds() {
        return new HashSet<>(vendorTicketsReleased.keySet());
    }

    /**
     * Resets the ticket pool. Used when reconfiguring the system.
     */
    public synchronized void resetPool() {
        tickets.clear();
        totalTicketsReleased = 0;
        totalTicketsPurchased = 0;
        nextTicketId = 1;
        isClosed = false;
        vendorTicketsReleased.clear();
        Logger.log("[TicketPool] Pool has been reset.");
        notifyAll(); // Notify all waiting threads that the pool has been reset
    }
}

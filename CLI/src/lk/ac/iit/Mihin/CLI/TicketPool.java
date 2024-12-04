package lk.ac.iit.Mihin.CLI;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final int maxCapacity;
    private final int totalTickets;
    private final Queue<String> tickets;
    private int totalTicketsReleased = 0;
    private int totalTicketsPurchased = 0;
    private int nextTicketId = 1;
    private boolean isClosed = false;

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

    /**
     * Adds a ticket to the pool if possible.
     *
     * @return The ticket string if added successfully, null otherwise.
     */
    public synchronized String addTicket() {
        if (totalTicketsReleased >= totalTickets) {
            isClosed = true;
            notifyAll();
            return null; // No more tickets can be added
        }

        if (tickets.size() < maxCapacity && totalTicketsReleased < totalTickets) {
            String ticket = "Ticket-" + nextTicketId++;
            tickets.add(ticket);
            totalTicketsReleased++;
            Logger.log("[TicketPool] Ticket added: " + ticket + " | Pool size: " + tickets.size());
            notifyAll(); // Notify all waiting threads that the pool state has changed
            return ticket;
        } else {
            return null;
        }
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

    public synchronized int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public synchronized int getTotalTicketsPurchased() {
        return totalTicketsPurchased;
    }

    public synchronized boolean isClosed() {
        return isClosed;
    }

    public synchronized void resetPool() {
        tickets.clear();
        totalTicketsReleased = 0;
        totalTicketsPurchased = 0;
        nextTicketId = 1;
        isClosed = false;
        Logger.log("[TicketPool] Pool has been reset.");
        notifyAll(); // Notify all waiting threads that the pool has been reset
    }

    public synchronized int getCurrentTickets() {
        return tickets.size();
    }

    public synchronized int getRemainingTickets() {
        return maxCapacity - tickets.size();
    }
}

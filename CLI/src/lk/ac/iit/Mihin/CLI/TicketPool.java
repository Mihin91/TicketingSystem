package lk.ac.iit.Mihin.CLI;


import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final List<Integer> tickets = new ArrayList<>(); // To store tickets
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Add tickets to the pool, ensuring no duplicates
    public synchronized boolean addTicket(int ticketId) {
        if (tickets.size() < maxCapacity && !tickets.contains(ticketId)) {
            tickets.add(ticketId);
            return true;
        }
        return false;
    }


    // Remove a ticket from the pool
    public synchronized Integer removeTicket() {
        if (!tickets.isEmpty()) {
            return tickets.remove(0); // Remove and return the first ticket
        }
        return null; // No tickets available
    }

    // Check if there are tickets in the pool
    public synchronized boolean hasTickets() {
        return !tickets.isEmpty();
    }

    // Check if there's capacity to add more tickets
    public synchronized boolean hasCapacity() {
        return tickets.size() < maxCapacity;
    }

    // Get the current size of the ticket pool
    public synchronized int getCurrentTickets() {
        return tickets.size();
    }
}

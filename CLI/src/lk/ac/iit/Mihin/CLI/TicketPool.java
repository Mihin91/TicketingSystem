package lk.ac.iit.Mihin.CLI;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final int maxCapacity; // Maximum capacity of the ticket pool
    private final Queue<String> tickets; // A queue to hold tickets
    private int totalTicketsReleased = 0; // Tracks total tickets released to the pool
    private int totalTicketsPurchased = 0; // Tracks total tickets purchased by customers

    // Constructor to initialize the ticket pool with a max capacity
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new LinkedList<>();
    }

    public synchronized void addTicket(String ticket) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            System.out.println("[TicketPool] Pool is full. Vendor waiting...");
            wait(); // Wait until space is available
        }
        tickets.add(ticket);
        totalTicketsReleased++;
        System.out.println("[TicketPool] Ticket added: " + ticket + " | Pool size: " + tickets.size());
        notifyAll(); // Notify any waiting threads
    }

    public synchronized String removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            System.out.println("[TicketPool] No tickets available. Customer waiting...");
            wait(); // Wait until tickets are available
        }
        String ticket = tickets.poll();
        totalTicketsPurchased++; // Track the number of tickets purchased
        System.out.println("[TicketPool] Ticket purchased: " + ticket + " | Pool size: " + tickets.size());
        notifyAll(); // Notify any waiting threads
        return ticket;
    }

    public synchronized int getCurrentTickets() {
        return tickets.size();
    }

    public synchronized int getRemainingTickets() {
        return maxCapacity - tickets.size();
    }

    public synchronized int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public synchronized int getTotalTicketsPurchased() {
        return totalTicketsPurchased; // Return the total tickets purchased
    }

    public synchronized void resetPool() {
        tickets.clear();
        totalTicketsReleased = 0;
        totalTicketsPurchased = 0; // Reset purchase counter
        System.out.println("[TicketPool] Pool has been reset.");
        notifyAll(); // Notify waiting threads
    }
}

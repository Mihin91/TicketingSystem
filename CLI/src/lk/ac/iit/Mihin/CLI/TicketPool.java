package lk.ac.iit.Mihin.CLI;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final int maxCapacity;
    private final Queue<String> tickets;
    private int totalTicketsReleased = 0;
    private int totalTicketsPurchased = 0;


    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new LinkedList<>();
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
    }

    public synchronized String removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            System.out.println("[TicketPool] No tickets available. Customer waiting...");
            wait();
        }
        String ticket = tickets.poll();
        totalTicketsPurchased++; // Track the number of tickets purchased
        System.out.println("[TicketPool] Ticket purchased: " + ticket + " | Pool size: " + tickets.size());
        notifyAll();
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
        return totalTicketsPurchased;
    }

    public synchronized void resetPool() {
        tickets.clear();
        totalTicketsReleased = 0;
        totalTicketsPurchased = 0;
        System.out.println("[TicketPool] Pool has been reset.");
        notifyAll();
    }
}

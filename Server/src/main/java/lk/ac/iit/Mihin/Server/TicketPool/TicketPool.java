package lk.ac.iit.Mihin.Server.TicketPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final List<Integer> tickets;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new ArrayList<>();
    }

    public void addTickets(int ticketCount) {
        lock.lock();
        try {
            if (tickets.size() + ticketCount <= maxCapacity) {
                for (int i = 0; i < ticketCount; i++) {
                    tickets.add(i);
                }
                System.out.println("Added " + ticketCount + " tickets to the pool.");
            } else {
                System.out.println("Cannot add more tickets, maximum capacity reached.");
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean removeTicket() {
        lock.lock();
        try {
            if (!tickets.isEmpty()) {
                tickets.remove(0);
                System.out.println("Removed a ticket from the pool.");
                return true;
            } else {
                System.out.println("No tickets left to remove.");
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean hasTickets() {
        return !tickets.isEmpty();
    }

    public boolean hasCapacity() {
        return tickets.size() < maxCapacity;
    }

    public int getCurrentTickets() {
        lock.lock();
        try {
            return tickets.size();
        } finally {
            lock.unlock();
        }
    }
}

package lk.ac.iit.Mihin.Server.Threads;

import lk.ac.iit.Mihin.Server.Model.TicketPool;

import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalDateTime;

public class CustomerThread implements Runnable {
    private final int customerId;
    private final TicketPool ticketPool;
    private int ticketCount = 0;
    private final int retrievalInterval;

    public CustomerThread(int customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = ThreadLocalRandom.current().nextInt(2000, 3001);
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            // Staggered start delay
            Thread.sleep(ThreadLocalRandom.current().nextInt(0, 500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (ticketPool) {
                    if (ticketPool.hasTickets()) {
                        Integer ticketId = ticketPool.removeTicket();
                        ticketCount++;
                        System.out.println("[Customer] " + customerId + " purchased Ticket-" + ticketId + " at " + LocalDateTime.now() + " | Tickets Left: " + ticketPool.getRemainingTickets());
                    } else {
                        System.out.println("[Customer] " + customerId + " could not purchase a ticket. No tickets available.");
                    }
                }
                Thread.sleep(retrievalInterval);
            } catch (InterruptedException e) {
                System.out.println("[Customer] " + customerId + " thread interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public int getCustomerId() {
        return customerId;
    }
}

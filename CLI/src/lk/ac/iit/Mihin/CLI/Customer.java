package lk.ac.iit.Mihin.CLI;

import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalDateTime;

public class Customer implements Runnable {
    private final int customerId;
    private final TicketPool ticketPool;
    private int ticketCount = 0;
    private final int retrievalInterval;

    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool) {
        super();
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
                        ticketPool.removeTicket();
                        ticketCount++;
                        System.out.println("Customer " + customerId + " purchased a ticket at " + LocalDateTime.now());
                    } else {
                        System.out.println("Customer " + customerId + " could not purchase a ticket due to unavailability");
                    }
                }
                Thread.sleep(retrievalInterval);
            } catch (InterruptedException e) {
                System.out.println("Customer " + customerId + " thread interrupted");
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

package lk.ac.iit.Mihin.CLI;

import java.time.LocalDateTime;

public class Customer implements Runnable {
    private final int customerId;
    private final int retrievalInterval;
    private final TicketPool ticketPool;
    private int ticketCount = 0; // Counter for the number of tickets purchased

    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool) {
        super();
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (ticketPool) {
                    if (ticketPool.hasTickets()) {
                        ticketPool.removeTicket();
                        ticketCount++; // Increment the counter
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

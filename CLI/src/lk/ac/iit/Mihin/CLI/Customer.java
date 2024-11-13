package lk.ac.iit.Mihin.CLI;

import java.time.LocalDateTime;

public class Customer implements Runnable {
    private final int customerId;
    private final int retrievalInterval;
    private final TicketPool ticketPool;

    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool) {
        super();
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (ticketPool) {
                    if (ticketPool.hasTickets()) {
                        System.out.println("Customer " + customerId + " purchased a ticket");
                        ticketPool.removeTicket();
                        // Log ticket sale directly
                        System.out.println("Ticket sold to customer " + customerId + " at " + LocalDateTime.now());
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
}


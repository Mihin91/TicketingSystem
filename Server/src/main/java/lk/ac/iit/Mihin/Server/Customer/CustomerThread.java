package lk.ac.iit.Mihin.Server.Customer;

import lk.ac.iit.Mihin.Server.TicketPool.TicketPool;
import java.time.LocalDateTime;

public class CustomerThread implements Runnable {
    private final int customerId;
    private final int retrievalInterval;
    private final TicketPool ticketPool;

    public CustomerThread(int customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // No need for synchronized block, as ticketPool handles synchronization itself
                if (ticketPool.hasTickets()) {
                    System.out.println("Customer " + customerId + " purchased a ticket.");
                    ticketPool.removeTicket((long) customerId);
                    System.out.println("Ticket sold to customer " + customerId + " at " + LocalDateTime.now());
                } else {
                    System.out.println("Customer " + customerId + " could not purchase a ticket due to unavailability.");
                }

                Thread.sleep(retrievalInterval);  // Simulate the interval between ticket retrieval attempts

            } catch (InterruptedException e) {
                System.out.println("Customer " + customerId + " thread interrupted.");
                Thread.currentThread().interrupt();  // Propagate the interrupt signal
                break;
            }
        }
    }
}

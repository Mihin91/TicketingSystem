package lk.ac.iit.Mihin.CLI;

import java.time.LocalDateTime;

public class Customer implements Runnable {
    private final int customerId;
    private final TicketPool ticketPool;
    private final int retrievalInterval; // How often the customer tries to buy tickets
    private int ticketCount = 0; // Tracks the number of tickets bought by this customer

    public Customer(int customerId, TicketPool ticketPool, int retrievalInterval) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    ticketCount++;
                    System.out.println("[Customer] " + customerId + " purchased ticket: " + ticket + " at " + LocalDateTime.now());
                } else {
                    System.out.println("[Customer] " + customerId + " could not purchase a ticket. Pool is empty.");
                }
                Thread.sleep(retrievalInterval); // Wait before trying again
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
}

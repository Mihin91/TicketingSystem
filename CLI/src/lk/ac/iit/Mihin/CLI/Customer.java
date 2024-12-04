package lk.ac.iit.Mihin.CLI;

public class Customer extends Participant {
    private final int retrievalInterval; // Interval between retrieval attempts in milliseconds

    // Constructor
    public Customer(int id, int retrievalInterval, TicketPool ticketPool) {
        super(id, ticketPool); // Initialize common fields
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String ticket = ticketPool.removeTicket();
                if (ticket == null) {
                    System.out.println("[Customer] " + id + " detected no more tickets. Stopping...");
                    break;
                }
                System.out.println("[Customer] " + id + " purchased " + ticket);

                // Wait for the specified retrieval interval
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            System.out.println("[Customer] " + id + " interrupted. Stopping...");
            Thread.currentThread().interrupt();
        }
    }
}

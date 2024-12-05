package lk.ac.iit.Mihin.CLI;

public class Customer extends Participant {
    private final int retrievalInterval; // Interval between retrievals in milliseconds

    // Constructor
    public Customer(int id, int retrievalInterval, TicketPool ticketPool) {
        super(id, ticketPool); // Initialize common fields
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String ticket = ticketPool.removeTicket();
                if (ticket == null) {
                    // No more tickets will be available
                    System.out.println("[Customer] " + id + " no more tickets to purchase. Stopping...");
                    break;
                } else {
                    // Print Customer purchase message
                    System.out.println("[Customer] " + id + " purchased: " + ticket);
                }

                // Wait for the specified retrieval interval
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            System.out.println("[Customer] " + id + " interrupted. Stopping...");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("[Customer] " + id + " encountered an error: " + e.getMessage());
        } finally {
            System.out.println("[Customer] " + id + " terminated.");
        }
    }
}

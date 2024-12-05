package lk.ac.iit.Mihin.CLI;

import java.util.Random;

public class Vendor extends Participant {
    private final int releaseInterval; // Interval between releases in milliseconds
    private final Random random = new Random();

    // Constructor
    public Vendor(int id, int releaseInterval, TicketPool ticketPool) {
        super(id, ticketPool); // Initialize common fields
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (ticketPool) {
                    if (ticketPool.isClosed()) {
                        System.out.println("[Vendor] " + id + " detected pool closure. Stopping...");
                        break;
                    }
                }

                // Generate a random number of tickets to release (e.g., between 1 and 3)
                int ticketsToRelease = random.nextInt(3) + 1; // Random number between 1 and 3

                for (int i = 0; i < ticketsToRelease; i++) {
                    String ticket = ticketPool.addTicket(id);
                    if (ticket == null) {
                        // Pool might be closed or no more tickets to release
                        synchronized (ticketPool) {
                            if (ticketPool.isClosed()) {
                                break;
                            }
                        }
                        break; // Break the for-loop
                    } else {
                        int totalVendorTicketsReleased = ticketPool.getVendorTicketsReleased(id);
                        // Print Vendor release message
                        System.out.println("[Vendor] " + id + " released: " + ticket
                                + " | Total tickets released by vendor: " + totalVendorTicketsReleased);
                    }
                }

                // Wait for the specified release interval
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            System.out.println("[Vendor] " + id + " interrupted. Stopping...");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("[Vendor] " + id + " encountered an error: " + e.getMessage());
        } finally {
            System.out.println("[Vendor] " + id + " terminated.");
        }
    }
}

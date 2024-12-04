package lk.ac.iit.Mihin.CLI;

public class Vendor extends Participant {
    private final int ticketsPerRelease; // Number of tickets released per interval
    private final int releaseInterval; // Interval between releases in milliseconds
    private int ticketsReleased = 0; // Tracks tickets released by this vendor

    // Constructor
    public Vendor(int id, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        super(id, ticketPool); // Initialize common fields
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (ticketPool) {
                    if (ticketPool.isClosed() && ticketPool.getTotalTicketsReleased() >= ticketPool.getTotalTickets()) {
                        System.out.println("[vendor] " + id + " detected pool closure. Stopping...");
                        break;
                    }
                }

                int ticketsToAdd = Math.min(ticketsPerRelease, ticketPool.getTotalTickets() - ticketPool.getTotalTicketsReleased());

                if (ticketsToAdd > 0) {
                    for (int i = 0; i < ticketsToAdd; i++) {
                        String ticket = ticketPool.addTicket();
                        if (ticket == null) {
                            System.out.println("[vendor] " + id + " unable to add more tickets. Pool might be closed or full. Stopping...");
                            return;
                        }
                        ticketsReleased++; // Increment the vendor's release count

                        // Print Vendor release message first
                        System.out.println("[vendor] " + id + " released: " + ticket + " | Total tickets released: " + ticketsReleased);

                        // Then print TicketPool addition message
                        System.out.println("[TicketPool] Ticket added: " + ticket + " | Pool size: " + ticketPool.getCurrentTickets());
                    }
                } else {
                    System.out.println("[vendor] " + id + " Pool is full. Vendor waiting...");
                }

                // Wait for the specified release interval
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            System.out.println("[vendor] " + id + " interrupted. Stopping...");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("[vendor] " + id + " terminated.");
        }
    }
}

package lk.ac.iit.Mihin.CLI;

import java.util.concurrent.ThreadLocalRandom;

public class Vendor implements Runnable {
    private final int vendorId; // Unique identifier for the vendor
    private final int ticketsPerRelease; // Number of tickets released per interval
    private final int releaseInterval; // Interval between releases in milliseconds
    private final TicketPool ticketPool; // Shared ticket pool for all vendors and customers

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Add tickets to the pool up to the max capacity or ticketsPerRelease
                int ticketsToAdd = Math.min(ticketsPerRelease, ticketPool.getRemainingTickets());
                for (int i = 0; i < ticketsToAdd; i++) {
                    String ticket = "Ticket-" + ThreadLocalRandom.current().nextInt(1, 1000);
                    ticketPool.addTicket(ticket); // Add ticket to the pool
                }

                if (ticketsToAdd > 0) {
                    System.out.println("[Vendor] " + vendorId + " released " + ticketsToAdd
                            + " tickets. Total tickets available: " + ticketPool.getCurrentTickets());
                } else {
                    System.out.println("[Vendor] " + vendorId + " found the pool full. Waiting...");
                }

                // Wait for the specified release interval
                Thread.sleep(releaseInterval);
            } catch (InterruptedException e) {
                System.out.println("[Vendor] " + vendorId + " interrupted. Stopping...");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}


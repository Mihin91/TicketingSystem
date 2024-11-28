package lk.ac.iit.Mihin.Server.Threads;

import lk.ac.iit.Mihin.Server.Model.TicketPool;

import java.util.concurrent.ThreadLocalRandom;

public class VendorThread implements Runnable {
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    private final TicketPool ticketPool;

    public VendorThread(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    public int getVendorId() {
        return vendorId;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (ticketPool) {
                    int ticketsToAdd = Math.min(ticketsPerRelease, ticketPool.getMaxCapacity() - ticketPool.getCurrentTickets());
                    if (ticketsToAdd <= 0) {
                        System.out.println("[Vendor] " + vendorId + " cannot add more tickets. Capacity reached.");
                        break; // Stop releasing tickets if capacity is reached
                    } else {
                        for (int i = 0; i < ticketsToAdd; i++) {
                            int ticketId = (vendorId * 1000) + i; // Unique ticket ID logic
                            if (ticketPool.addTicket(ticketId)) {
                                System.out.println("[Vendor] " + vendorId + " released Ticket-" + ticketId);
                            }
                        }
                        System.out.println("[Vendor] " + vendorId + " released " + ticketsToAdd + " tickets at " + System.currentTimeMillis());
                    }
                }

                // Stagger vendor releases by adding a small delay between releases
                Thread.sleep(releaseInterval + ThreadLocalRandom.current().nextInt(0, 500)); // Add a random jitter
            } catch (InterruptedException e) {
                System.out.println("[Vendor] " + vendorId + " thread interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

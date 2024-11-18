package lk.ac.iit.Mihin.Server.Vendor;

import lk.ac.iit.Mihin.Server.TicketPool.TicketPool;

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

    @Override
    public void run() {
        while (true) {
            try {
                // Using TicketPool's own lock (ReentrantLock)
                if (ticketPool.hasCapacity()) {
                    // Adding tickets with vendorId
                    ticketPool.addTickets(ticketsPerRelease, (long) vendorId);
                    System.out.println("Vendor " + vendorId + " has released " + ticketsPerRelease + " tickets at " + System.currentTimeMillis());
                } else {
                    System.out.println("Vendor " + vendorId + " has exceeded pool capacity");
                    break;
                }

                // Simulate the interval between ticket releases
                Thread.sleep(releaseInterval);

            } catch (InterruptedException e) {
                System.out.println("Vendor " + vendorId + " was interrupted");
                Thread.currentThread().interrupt();  // Propagate the interrupt signal
                break;
            }
        }
    }
}

package lk.ac.iit.Mihin.CLI;

public class Vendor implements Runnable {
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    private final TicketPool ticketPool;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        super();
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                boolean ticketsAdded = false;
                synchronized (ticketPool) {
                    for (int i = 0; i < ticketsPerRelease; i++) {
                        if (ticketPool.hasCapacity() && ticketPool.addTicket(i)) { // Simplified ticket ID
                            ticketsAdded = true;
                        } else {
                            break;
                        }
                    }
                }
                if (ticketsAdded) {
                    System.out.println("Vendor " + vendorId + " has released " + ticketsPerRelease + " tickets at " + System.currentTimeMillis());
                } else {
                    System.out.println("Vendor " + vendorId + " could not add more tickets.");
                }
                Thread.sleep(releaseInterval);
            } catch (InterruptedException e) {
                System.out.println("Vendor " + vendorId + " thread interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

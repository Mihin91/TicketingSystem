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
        while (true) {
            try {
                synchronized (ticketPool) {
                    if (ticketPool.hasCapacity()) {
                        ticketPool.addTickets(ticketsPerRelease);
                        System.out.println("Vendor " + vendorId + " has released " + ticketsPerRelease + " tickets");
                        // Log ticket release directly
                        System.out.println("Vendor " + vendorId + " added " + ticketsPerRelease + " tickets at " + System.currentTimeMillis());
                    } else {
                        System.out.println("Vendor " + vendorId + " has exceeded pool capacity");
                        break;
                    }
                }
                Thread.sleep(releaseInterval);

            } catch (InterruptedException e) {
                System.out.println("Vendor " + vendorId + " is interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

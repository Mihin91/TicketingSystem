package lk.ac.iit.Mihin.CLI;

import java.util.Random;

public class Vendor extends Participant {
    private final int releaseInterval;
    private final Random random = new Random();

    public Vendor(int id, int releaseInterval, TicketPool ticketPool) {
        super(id, ticketPool);
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

                int ticketsToRelease = random.nextInt(5) + 1;

                for (int i = 0; i < ticketsToRelease; i++) {
                    String ticket = ticketPool.addTicket(id);
                    if (ticket == null) {
                        synchronized (ticketPool) {
                            if (ticketPool.isClosed()) {
                                break;
                            }
                        }
                        break;
                    } else {
                        int totalVendorTicketsReleased = ticketPool.getVendorTicketsReleased(id);
                        System.out.println("[Vendor] " + id + " released: " + ticket
                                + " | Total tickets released by vendor: " + totalVendorTicketsReleased);
                    }
                }
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

// src/main/java/lk/ac/iit/Mihin/Server/Runnables/VendorRunnable.java
package lk.ac.iit.Mihin.Server.Runnables;

import lk.ac.iit.Mihin.Server.Model.Ticket;
import lk.ac.iit.Mihin.Server.Services.TicketPoolService;
import lk.ac.iit.Mihin.Server.Services.LogService;

public class VendorRunnable implements Runnable {

    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    private final TicketPoolService ticketPoolService;
    private final LogService logService;

    public VendorRunnable(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPoolService ticketPoolService, LogService logService) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPoolService = ticketPoolService;
        this.logService = logService;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int ticketsToAdd = Math.min(ticketsPerRelease, ticketPoolService.getRemainingCapacity());
                for (int i = 0; i < ticketsToAdd; i++) {
                    Ticket ticket = ticketPoolService.addTicket(vendorId);
                    if (ticket == null) {
                        System.out.println("[Vendor] " + vendorId + " could not add more tickets. Pool might be full or closed.");
                        break;
                    }
                    System.out.println("[Vendor] " + vendorId + " released: Ticket-" + ticket.getId() + " | Total tickets released: " + ticketPoolService.getTotalTicketsReleased());
                    logService.addLog("[Vendor] " + vendorId + " released: Ticket-" + ticket.getId());
                }
                Thread.sleep(releaseInterval);
            } catch (InterruptedException e) {
                System.out.println("[Vendor] " + vendorId + " thread interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

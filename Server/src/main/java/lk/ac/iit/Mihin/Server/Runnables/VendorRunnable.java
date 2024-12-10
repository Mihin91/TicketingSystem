// src/main/java/lk/ac/iit/Mihin/Server/Runnables/VendorRunnable.java
package lk.ac.iit.Mihin.Server.Runnables;

import lk.ac.iit.Mihin.Server.Model.Ticket;
import lk.ac.iit.Mihin.Server.Services.TicketPoolService;
import lk.ac.iit.Mihin.Server.Services.LogService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VendorRunnable implements Runnable {

    private final int vendorId;
    private final int ticketReleaseRate; // Interval between ticket releases in milliseconds
    private final TicketPoolService ticketPoolService;
    private final LogService logService;

    public VendorRunnable(int vendorId, int ticketReleaseRate, TicketPoolService ticketPoolService, LogService logService) {
        this.vendorId = vendorId;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPoolService = ticketPoolService;
        this.logService = logService;
    }

    @Override
    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Ticket ticket = ticketPoolService.addTicket(vendorId);
                if (ticket == null) {
                    String logMessage = "[Vendor] " + vendorId + " could not add more tickets. Pool might be full or closed.";
                    System.out.println(logMessage);
                    logService.addLog(logMessage);
                    break;
                }
                String timeStamp = dateFormat.format(new Date());
                String logMessage = "[Vendor] " + vendorId + " released: Ticket-" + ticket.getId() +
                        " at " + timeStamp +
                        " | Total tickets released: " + ticketPoolService.getTotalTicketsReleased() +
                        " | Available tickets: " + ticketPoolService.getCurrentTickets();
                System.out.println(logMessage);
                logService.addLog(logMessage);

                // Sleep for the specified release rate
                Thread.sleep(ticketReleaseRate);
            } catch (InterruptedException e) {
                String logMessage = "[Vendor] " + vendorId + " thread interrupted";
                System.out.println(logMessage);
                logService.addLog(logMessage);
                Thread.currentThread().interrupt();
                break;
            }
        }
        logService.addLog("[Vendor] " + vendorId + " terminated.");
    }
}

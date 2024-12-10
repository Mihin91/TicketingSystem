// src/main/java/lk/ac/iit/Mihin/Server/Runnables/CustomerRunnable.java
package lk.ac.iit.Mihin.Server.Runnables;

import lk.ac.iit.Mihin.Server.Model.Ticket;
import lk.ac.iit.Mihin.Server.Services.TicketPoolService;
import lk.ac.iit.Mihin.Server.Services.LogService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerRunnable implements Runnable {

    private final int customerId;
    private final int retrievalInterval;
    private final TicketPoolService ticketPoolService;
    private final LogService logService;

    public CustomerRunnable(int customerId, int retrievalInterval, TicketPoolService ticketPoolService, LogService logService) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPoolService = ticketPoolService;
        this.logService = logService;
    }

    @Override
    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Ticket ticket = ticketPoolService.removeTicket(customerId);
                String timeStamp = dateFormat.format(new Date());
                if (ticket != null) {
                    String logMessage = "[Customer] " + customerId + " purchased ticket: " + ticket.getId() + " at " + timeStamp;
                    System.out.println(logMessage);
                    logService.addLog(logMessage);
                } else {
                    String logMessage = "[Customer] " + customerId + " could not purchase a ticket at " + timeStamp + ". Pool is empty.";
                    System.out.println(logMessage);
                    logService.addLog(logMessage);
                }
                Thread.sleep(retrievalInterval);
            } catch (InterruptedException e) {
                String logMessage = "[Customer] " + customerId + " thread interrupted";
                System.out.println(logMessage);
                logService.addLog(logMessage);
                Thread.currentThread().interrupt();
                break;
            }
        }
        logService.addLog("[Customer] " + customerId + " terminated.");
    }
}

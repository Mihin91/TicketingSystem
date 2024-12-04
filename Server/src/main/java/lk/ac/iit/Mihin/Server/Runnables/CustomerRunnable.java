// src/main/java/lk/ac/iit/Mihin/Server/Runnables/CustomerRunnable.java
package lk.ac.iit.Mihin.Server.Runnables;

import lk.ac.iit.Mihin.Server.Model.Ticket;
import lk.ac.iit.Mihin.Server.Services.TicketPoolService;
import lk.ac.iit.Mihin.Server.Services.LogService;

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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Ticket ticket = ticketPoolService.removeTicket(customerId);
                if (ticket != null) {
                    System.out.println("[Customer] " + customerId + " purchased ticket: " + ticket.getId());
                    logService.addLog("[Customer] " + customerId + " purchased ticket: " + ticket.getId());
                } else {
                    System.out.println("[Customer] " + customerId + " could not purchase a ticket. Pool is empty.");
                    logService.addLog("[Customer] " + customerId + " could not purchase a ticket. Pool is empty.");
                }
                Thread.sleep(retrievalInterval);
            } catch (InterruptedException e) {
                System.out.println("[Customer] " + customerId + " thread interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

package lk.ac.iit.Mihin.TicketingSystem.Customer;

import lk.ac.iit.Mihin.TicketingSystem.Repositories.TicketSalesRepository;
import lk.ac.iit.Mihin.TicketingSystem.TicketPool.TicketPool;
import lk.ac.iit.Mihin.TicketingSystem.database.TicketSales;

import java.time.LocalDateTime;


public class Customer implements Runnable {
    private final int customerId;
    private final int retrievalInterval;
    private final TicketPool ticketPool;
    private TicketSalesRepository ticketSalesRepository;

    public Customer(int customerId, int retrievalInterval, TicketPool ticketPool, TicketSalesRepository ticketSalesRepository){
        super();
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
        this.ticketSalesRepository = ticketSalesRepository;
    }

    @Override
    public void run(){
        while(true) {
            try {
                synchronized (ticketPool) {
                    if (ticketPool.hasTickets()) {
                        System.out.println("Customer " + customerId + "purchased a ticket");
                        ticketPool.removeTicket();

                        ticketSalesRepository.save(new TicketSales(customerId, LocalDateTime.now()));
                    } else {
                        System.out.println("Customer " + customerId + "could not purchase a ticket due to ticket unavailability ");
                    }
                }
                Thread.sleep(retrievalInterval);

            } catch (InterruptedException e) {
                System.out.println("Customer " + customerId + "thread interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

package lk.ac.iit.Mihin.Server.TicketPool;

import lk.ac.iit.Mihin.Server.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPool {
    private final TicketRepository ticketRepository;

    @Value("${ticket.pool.maxCapacity}")
    private int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    public TicketPool(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public String addTickets(int ticketCount, Long vendorId) {
        lock.lock();
        try {
            long currentTicketCount = ticketRepository.count();
            if (currentTicketCount + ticketCount <= maxCapacity) {
                for (int i = 0; i < ticketCount; i++) {
                    Tickets ticket = new Tickets();
                    ticket.setStatus("Available");
                    ticket.setVendorId(vendorId);
                    ticketRepository.save(ticket);  // Save to the database
                }
                System.out.println("Added " + ticketCount + " tickets to the pool.");
                return "Tickets added successfully";
            } else {
                System.out.println("Cannot add more tickets, maximum capacity reached.");
                return "Max capacity reached. Cannot add more tickets.";
            }
        } finally {
            lock.unlock();
        }
    }

    public String removeTicket(Long customerId) {
        lock.lock();
        try {
            Tickets ticket = ticketRepository.findFirstByStatus("AVAILABLE");
            if (ticket != null) {
                ticket.setStatus("PURCHASED");
                ticket.setCustomerId(customerId);
                ticketRepository.save(ticket);
                System.out.println("Customer " + customerId + " purchased a ticket.");
                return "Customer " + customerId + " purchased a ticket.";
            } else {
                System.out.println("No tickets available for purchase.");
                return "No tickets available for purchase.";
            }
        } finally {
            lock.unlock();
        }
    }


    public boolean hasTickets() {
        return ticketRepository.countByStatus("Available") > 0;
    }

    public boolean hasCapacity() {
        return ticketRepository.count() >= maxCapacity;
    }

    public int getCurrentTickets() {
        return (int) ticketRepository.count();
}
}
package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Model.Customer;
import lk.ac.iit.Mihin.Server.Model.TicketPool;
import lk.ac.iit.Mihin.Server.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TicketPool ticketPool;
    private final Map<Integer, Thread> customerThreads = new ConcurrentHashMap<>();

    @Autowired
    public CustomerService(CustomerRepository customerRepository, TicketPool ticketPool) {
        this.customerRepository = customerRepository;
        this.ticketPool = ticketPool;
    }

    public void startCustomer(int customerId, int retrievalInterval) {
        Customer customer = new Customer(customerId, ticketPool, retrievalInterval);
        Thread customerThread = new Thread(customer);
        customerThreads.put(customerId, customerThread);
        customerThread.start();
        // Save customer to the repository if needed
        customerRepository.save(customer);
    }

    public void stopAllCustomers() {
        for (Map.Entry<Integer, Thread> entry : customerThreads.entrySet()) {
            Thread thread = entry.getValue();
            thread.interrupt();
        }
        customerThreads.clear();
    }

    public void stopCustomer(int customerId) {
        Thread thread = customerThreads.get(customerId);
        if (thread != null) {
            thread.interrupt();
            customerThreads.remove(customerId);
        }
    }

    // Additional methods for managing customers
}

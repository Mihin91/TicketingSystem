// src/main/java/lk/ac/iit/Mihin/Server/Services/CustomerService.java
package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Model.Customer;
import lk.ac.iit.Mihin.Server.Repositories.CustomerRepository;
import lk.ac.iit.Mihin.Server.Runnables.CustomerRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TicketPoolService ticketPoolService;
    private final LogService logService;
    private final Map<Integer, Thread> customerThreads = new ConcurrentHashMap<>();

    @Autowired
    public CustomerService(CustomerRepository customerRepository, TicketPoolService ticketPoolService, LogService logService) {
        this.customerRepository = customerRepository;
        this.ticketPoolService = ticketPoolService;
        this.logService = logService;
    }

    /**
     * Starts a customer thread.
     *
     * @param customerId        ID of the customer.
     * @param retrievalInterval Interval between retrievals in milliseconds.
     */
    public void startCustomer(int customerId, int retrievalInterval) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setRetrievalInterval(retrievalInterval);
        // Save customer to repository
        customerRepository.save(customer);

        // Instantiate the runnable manually
        CustomerRunnable customerRunnable = new CustomerRunnable(customerId, retrievalInterval, ticketPoolService, logService);
        Thread customerThread = new Thread(customerRunnable, "Customer-" + customerId);
        customerThreads.put(customerId, customerThread);
        customerThread.start();
        logService.addLog("[System] Customer-" + customerId + " started.");
    }

    /**
     * Stops all customer threads.
     */
    public void stopAllCustomers() {
        for (Map.Entry<Integer, Thread> entry : customerThreads.entrySet()) {
            Thread thread = entry.getValue();
            thread.interrupt();
            logService.addLog("[System] Customer-" + entry.getKey() + " stopped.");
        }
        customerThreads.clear();
    }

    /**
     * Stops a specific customer thread.
     *
     * @param customerId ID of the customer to stop.
     */
    public void stopCustomer(int customerId) {
        Thread thread = customerThreads.get(customerId);
        if (thread != null) {
            thread.interrupt();
            customerThreads.remove(customerId);
            logService.addLog("[System] Customer-" + customerId + " stopped.");
        }
    }

}

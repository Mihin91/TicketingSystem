package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Customer.Customer;
import lk.ac.iit.Mihin.Server.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Add Customer
    public String addCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Customer added successfully";
    }

    // Get Customer by ID
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null); // Return null if not found
    }

    // Update Customer
    public String updateCustomer(Customer customer) {
        // Check if the customer exists
        if (customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer); // Update if found
            return "Customer updated successfully";
        } else {
            return "Customer not found"; // Return an error if the customer is not found
        }
    }

    // Delete Customer by ID
    public String deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return "Customer deleted successfully";
        } else {
            return "Customer not found"; // Return an error if the customer is not found
        }
    }
}

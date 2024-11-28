package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.DTO.CustomerDTO;
import lk.ac.iit.Mihin.Server.Model.Customer;
import lk.ac.iit.Mihin.Server.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Add Customer
    public String addCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getName());
        customerRepository.save(customer);
        return "Customer added successfully";
    }

    // Get Customer by ID
    public CustomerDTO getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return null;
        }
        return new CustomerDTO(customer.getId(), customer.getName());
    }

    // Update Customer
    public String updateCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsById(customerDTO.getId())) {
            Customer customer = new Customer(customerDTO.getName());
            customer.setId(customerDTO.getId());
            customerRepository.save(customer);
            return "Customer updated successfully";
        } else {
            return "Customer not found";
        }
    }

    // Delete Customer by ID
    public String deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return "Customer deleted successfully";
        } else {
            return "Customer not found";
        }
    }
}
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

    public String addCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Customer added successfully";
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public String deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }
}

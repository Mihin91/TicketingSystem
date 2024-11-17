package lk.ac.iit.Mihin.Server.Controllers;


import lk.ac.iit.Mihin.Server.Customer.CustomerEntity;
import lk.ac.iit.Mihin.Server.Services.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public String addCustomer(@RequestBody CustomerEntity customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }
}

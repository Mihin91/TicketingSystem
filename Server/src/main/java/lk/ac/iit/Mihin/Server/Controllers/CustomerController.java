package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Customer.Customer;
import lk.ac.iit.Mihin.Server.Services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If customer not found
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        String response = customerService.addCustomer(customer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
        String response = customerService.updateCustomer(customer);
        if (response.equals("Customer not found")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Customer not found
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
        String response = customerService.deleteCustomer(id);
        if (response.equals("Customer not found")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Customer not found
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

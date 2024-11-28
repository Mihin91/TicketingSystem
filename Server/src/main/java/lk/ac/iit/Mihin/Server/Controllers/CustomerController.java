package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.CustomerDTO;
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
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id) {
        CustomerDTO customerDTO = customerService.getCustomer(id);
        if (customerDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customerDTO) {
        String response = customerService.addCustomer(customerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        String response = customerService.updateCustomer(customerDTO);
        if (response.equals("Customer not found")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
        String response = customerService.deleteCustomer(id);
        if (response.equals("Customer not found")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

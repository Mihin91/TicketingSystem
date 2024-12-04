package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.CustomerDTO;
import lk.ac.iit.Mihin.Server.Services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.startCustomer(customerDTO.getCustomerId(), customerDTO.getRetrievalInterval());
        return ResponseEntity.ok("Customer started");
    }

    // Additional endpoints for customers
}

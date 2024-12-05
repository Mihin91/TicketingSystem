// src/main/java/lk/ac/iit/Mihin/Server/Controllers/SystemController.java
package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Model.Configuration;
import lk.ac.iit.Mihin.Server.Services.ConfigurationService;
import lk.ac.iit.Mihin.Server.Services.CustomerService;
import lk.ac.iit.Mihin.Server.Services.LogService;
import lk.ac.iit.Mihin.Server.Services.TicketPoolService;
import lk.ac.iit.Mihin.Server.Services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    private final ConfigurationService configurationService;
    private final VendorService vendorService;
    private final CustomerService customerService;
    private final TicketPoolService ticketPoolService;
    private final LogService logService;

    @Autowired
    public SystemController(ConfigurationService configurationService,
                            VendorService vendorService,
                            CustomerService customerService,
                            TicketPoolService ticketPoolService,
                            LogService logService) {
        this.configurationService = configurationService;
        this.vendorService = vendorService;
        this.customerService = customerService;
        this.ticketPoolService = ticketPoolService;
        this.logService = logService;
    }

    /**
     * Starts the simulation based on the latest configuration.
     *
     * @return JSON response indicating success or failure.
     */
    @PostMapping("/start")
    public ResponseEntity<?> startSystem() {
        // Fetch the latest configuration
        Configuration config = configurationService.getLatestConfiguration();
        if (config == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No configuration found. Please save a configuration first.");
            return ResponseEntity.badRequest().body(response);
        }

        // Initialize the ticket pool
        ticketPoolService.initializePool(config.getMaxTicketCapacity(), config.getTotalTickets());

        // Start vendor threads
        int numberOfVendors = config.getNumberOfVendors();
        for (int i = 1; i <= numberOfVendors; i++) {
            vendorService.startVendor(i, config.getTicketReleaseRate(), config.getReleaseInterval());
        }

        // Start customer threads
        int numberOfCustomers = config.getNumberOfCustomers();
        for (int i = 1; i <= numberOfCustomers; i++) {
            customerService.startCustomer(i, config.getCustomerRetrievalRate());
        }

        logService.addLog("Simulation started with configuration ID: " + config.getId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation started successfully.");
        return ResponseEntity.ok(response);
    }

    /**
     * Stops the simulation by interrupting all vendor and customer threads.
     *
     * @return JSON response indicating success or failure.
     */
    @PostMapping("/stop")
    public ResponseEntity<?> stopSystem() {
        try {
            // Stop vendor threads
            vendorService.stopAllVendors();

            // Stop customer threads
            customerService.stopAllCustomers();

            // Close the ticket pool
            ticketPoolService.resetPool();

            logService.addLog("Simulation stopped.");
            return ResponseEntity.ok("Simulation stopped successfully.");
        } catch (Exception e) {
            logService.addLog("Error stopping the simulation: " + e.getMessage());
            return ResponseEntity.status(500).body("Error stopping the simulation: " + e.getMessage());
        }
    }

}

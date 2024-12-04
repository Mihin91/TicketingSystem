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
     * @return Response indicating success or failure.
     */
    @PostMapping("/start")
    public ResponseEntity<?> startSystem() {
        // Fetch the latest configuration
        Configuration config = configurationService.getLatestConfiguration();
        if (config == null) {
            return ResponseEntity.badRequest().body("No configuration found. Please save a configuration first.");
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
        return ResponseEntity.ok("Simulation started successfully.");
    }

    /**
     * Stops the simulation by interrupting all vendor and customer threads.
     *
     * @return Response indicating success or failure.
     */
    @PostMapping("/stop")
    public ResponseEntity<?> stopSystem() {
        // Stop vendor threads
        vendorService.stopAllVendors();

        // Stop customer threads
        customerService.stopAllCustomers();

        // Reset the ticket pool
        ticketPoolService.resetPool();

        logService.addLog("Simulation stopped.");
        return ResponseEntity.ok("Simulation stopped successfully.");
    }
}

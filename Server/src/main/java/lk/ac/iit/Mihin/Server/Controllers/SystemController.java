// src/main/java/lk/ac/iit/Mihin/Server/Controllers/SystemController.java
package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Events.TicketsSoldOutEvent;
import lk.ac.iit.Mihin.Server.Model.Configuration;
import lk.ac.iit.Mihin.Server.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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

    @EventListener
    public void handleTicketsSoldOutEvent(TicketsSoldOutEvent event) {
        stopSimulation();
        logService.addLog("[System] All tickets have been sold. Simulation stopped automatically.");
    }

    private synchronized void stopSimulation() {
        try {
            vendorService.stopAllVendors();
            customerService.stopAllCustomers();
            // Removed the resetPool call to preserve the current status
            // ticketPoolService.resetPool();
            logService.addLog("Simulation stopped.");
        } catch (Exception e) {
            logService.addLog("Error stopping the simulation: " + e.getMessage());
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> startSystem() {
        Configuration config = configurationService.getLatestConfiguration();
        if (config == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No configuration found. Please save a configuration first.");
            return ResponseEntity.badRequest().body(response);
        }

        ticketPoolService.initializePool(config.getMaxTicketCapacity(), config.getTotalTickets());

        int numberOfVendors = config.getNumberOfVendors();
        for (int i = 1; i <= numberOfVendors; i++) {
            vendorService.startVendor(i, config.getTicketReleaseRate());
        }

        int numberOfCustomers = config.getNumberOfCustomers();
        for (int i = 1; i <= numberOfCustomers; i++) {
            customerService.startCustomer(i, config.getCustomerRetrievalRate());
        }

        logService.addLog("Simulation started with configuration ID: " + config.getId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation started successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/stop")
    public ResponseEntity<?> stopSystem() {
        stopSimulation();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation stopped successfully.");
        return ResponseEntity.ok(response);
    }
}

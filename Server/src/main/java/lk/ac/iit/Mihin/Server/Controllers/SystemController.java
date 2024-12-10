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

    /**
     * Handles the event when all tickets are sold out.
     *
     * @param event The TicketsSoldOutEvent.
     */
    @EventListener
    public void handleTicketsSoldOutEvent(TicketsSoldOutEvent event) {
        stopSimulation();
        logService.addLog("[System] All tickets have been sold. Simulation stopped automatically.");
    }

    /**
     * Stops the simulation by interrupting all vendor and customer threads.
     */
    private synchronized void stopSimulation() {
        try {
            vendorService.stopAllVendors();
            customerService.stopAllCustomers();
            // Do not reset the ticket pool here to preserve the current status
            logService.addLog("Simulation stopped.");
        } catch (Exception e) {
            logService.addLog("Error stopping the simulation: " + e.getMessage());
        }
    }

    /**
     * Starts the simulation by initializing the ticket pool and starting all vendors and customers.
     *
     * @return Response indicating the result of the start operation.
     */
    @PostMapping("/start")
    public ResponseEntity<?> startSystem() {
        Configuration config = configurationService.getLatestConfiguration();
        if (config == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No configuration found. Please save a configuration first.");
            return ResponseEntity.badRequest().body(response);
        }

        if (!ticketPoolService.isInitialized()) {
            ticketPoolService.initializePool(config.getMaxTicketCapacity(), config.getTotalTickets());
        } else {
            logService.addLog("[System] Ticket pool already initialized. Continuing simulation.");
        }

        int numberOfVendors = config.getNumberOfVendors();
        for (int i = 1; i <= numberOfVendors; i++) {
            if (!vendorService.isVendorRunning(i)) { // Check if vendor is already running
                vendorService.startVendor(i, config.getTicketReleaseRate());
            }
        }

        int numberOfCustomers = config.getNumberOfCustomers();
        for (int i = 1; i <= numberOfCustomers; i++) {
            if (!customerService.isCustomerRunning(i)) { // Check if customer is already running
                customerService.startCustomer(i, config.getCustomerRetrievalRate());
            }
        }

        logService.addLog("Simulation started with configuration ID: " + config.getId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation started successfully.");
        return ResponseEntity.ok(response);
    }

    /**
     * Stops the simulation by interrupting all vendor and customer threads.
     *
     * @return Response indicating the result of the stop operation.
     */
    @PostMapping("/stop")
    public ResponseEntity<?> stopSystem() {
        vendorService.stopAllVendors();
        customerService.stopAllCustomers();
        return ResponseEntity.ok(Map.of("message", "System stopped"));
    }

    /**
     * Resets the simulation by clearing the ticket pool and logs, and stopping all threads.
     *
     * @return Response indicating the result of the reset operation.
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetSystem() {
        try {
            // Reset ticket pool and stop all vendors and customers
            ticketPoolService.resetPool();
            vendorService.stopAllVendors();
            customerService.stopAllCustomers();

            // Clear logs and broadcast to frontend
            logService.clearLogs();

            // Log the reset action
            logService.addLog("[System] System reset successfully.");

            Map<String, String> response = new HashMap<>();
            response.put("message", "System reset successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logService.addLog("[Error] System reset failed: " + e.getMessage());
            return ResponseEntity.status(500).body("Error resetting the system: " + e.getMessage());
        }
    }
}

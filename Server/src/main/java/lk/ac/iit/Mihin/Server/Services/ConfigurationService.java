// src/main/java/lk/ac/iit/Mihin/Server/Services/ConfigurationService.java
package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Model.Configuration;
import lk.ac.iit.Mihin.Server.Repositories.ConfigurationRepository;
import lk.ac.iit.Mihin.Server.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;
    private final CustomerService customerService;
    private final VendorService vendorService;
    private final TicketRepository ticketRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository,
                                CustomerService customerService,
                                VendorService vendorService,
                                TicketRepository ticketRepository) {
        this.configurationRepository = configurationRepository;
        this.customerService = customerService;
        this.vendorService = vendorService;
        this.ticketRepository = ticketRepository;
    }

    /**
     * Saves a new configuration.
     *
     * @param config The configuration to save.
     * @return The saved configuration.
     */
    public Configuration saveConfiguration(Configuration config) {
        return configurationRepository.save(config);
    }

    /**
     * Retrieves a configuration by its ID.
     *
     * @param id The ID of the configuration.
     * @return The configuration or null if not found.
     */
    public Configuration getConfiguration(int id) {
        return configurationRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves the latest configuration.
     *
     * @return The latest configuration or null if none exist.
     */
    public Configuration getLatestConfiguration() {
        return configurationRepository.findTopByOrderByIdDesc();
    }

    /**
     * Retrieves all configurations.
     *
     * @return A list of all configurations.
     */
    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }

    /**
     * Deletes a configuration and all related entities.
     *
     * @param id The ID of the configuration to delete.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteConfiguration(int id) {
        if (configurationRepository.existsById(id)) {
            // Stop all running customers and vendors
            customerService.stopAllCustomers();
            vendorService.stopAllVendors();

            // Delete all tickets
            ticketRepository.deleteAll();

            // Delete all customers
            customerService.deleteAllCustomers();

            // Delete all vendors
            vendorService.deleteAllVendors();

            // Finally, delete the configuration
            configurationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

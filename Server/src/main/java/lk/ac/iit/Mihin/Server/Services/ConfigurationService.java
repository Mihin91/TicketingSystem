// src/main/java/lk/ac/iit/Mihin/Server/Services/ConfigurationService.java
package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Model.Configuration;
import lk.ac.iit.Mihin.Server.Repositories.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
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
     * Deletes a configuration by its ID.
     *
     * @param id The ID of the configuration to delete.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteConfiguration(int id) {
        if (configurationRepository.existsById(id)) {
            configurationRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

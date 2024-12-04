// src/main/java/lk/ac/iit/Mihin/Server/Services/ConfigurationService.java
package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Model.Configuration;
import lk.ac.iit.Mihin.Server.Repositories.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public Configuration saveConfiguration(Configuration config) {
        return configurationRepository.save(config);
    }

    public Configuration getConfiguration(int id) {
        return configurationRepository.findById(id).orElse(null);
    }

    public Configuration getLatestConfiguration() {
        return configurationRepository.findTopByOrderByIdDesc();
    }


}

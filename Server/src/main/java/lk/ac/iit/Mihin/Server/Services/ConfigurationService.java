package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.DTO.ConfigurationDTO;
import lk.ac.iit.Mihin.Server.Model.Configuration;
import lk.ac.iit.Mihin.Server.Repositories.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    // Convert Configuration to ConfigurationDTO
    private ConfigurationDTO toDTO(Configuration configuration) {
        return new ConfigurationDTO(
                configuration.getTotalTickets(),
                configuration.getTicketReleaseRate(),
                configuration.getCustomerRetrievalRate(),
                configuration.getMaxTicketCapacity()
        );
    }

    // Convert ConfigurationDTO to Configuration entity
    private Configuration fromDTO(ConfigurationDTO dto) {
        return new Configuration(
                dto.getTotalTickets(),
                dto.getTicketReleaseRate(),
                dto.getCustomerRetrievalRate(),
                dto.getMaxTicketCapacity()
        );
    }

    // Save a configuration (Create or Update)
    public ConfigurationDTO saveConfiguration(ConfigurationDTO configurationDTO) {
        Configuration configuration = fromDTO(configurationDTO);
        Configuration savedConfiguration = configurationRepository.save(configuration);
        return toDTO(savedConfiguration);
    }

    // Get a configuration by ID
    public Optional<ConfigurationDTO> getConfiguration(Long id) {
        return configurationRepository.findById(id).map(this::toDTO);
    }

    // Delete a configuration by ID
    public void deleteConfiguration(Long id) {
        configurationRepository.deleteById(id);
    }
}

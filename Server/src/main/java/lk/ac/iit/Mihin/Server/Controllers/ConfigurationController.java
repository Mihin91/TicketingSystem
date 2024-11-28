package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.ConfigurationDTO;
import lk.ac.iit.Mihin.Server.Services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/configurations")
@CrossOrigin
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    // Create or update a configuration
    @PostMapping
    public ResponseEntity<ConfigurationDTO> createOrUpdateConfiguration(@RequestBody ConfigurationDTO dto) {
        ConfigurationDTO savedConfiguration = configurationService.saveConfiguration(dto);
        return ResponseEntity.ok(savedConfiguration);
    }

    // Get a configuration by ID
    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationDTO> getConfiguration(@PathVariable Long id) {
        Optional<ConfigurationDTO> configuration = configurationService.getConfiguration(id);
        return configuration.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a configuration by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
        configurationService.deleteConfiguration(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

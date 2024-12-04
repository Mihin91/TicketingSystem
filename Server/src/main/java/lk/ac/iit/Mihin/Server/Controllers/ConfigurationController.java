// src/main/java/lk/ac/iit/Mihin/Server/Controllers/ConfigurationController.java
package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.ConfigurationDTO;
import lk.ac.iit.Mihin.Server.Model.Configuration;
import lk.ac.iit.Mihin.Server.Services.ConfigurationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    private final ConfigurationService configurationService;
    private final ModelMapper modelMapper;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService, ModelMapper modelMapper) {
        this.configurationService = configurationService;
        this.modelMapper = modelMapper;
    }

    /**
     * Saves a new configuration.
     *
     * @param configDTO Configuration data transfer object.
     * @param result    BindingResult to hold validation errors.
     * @return The saved configuration or validation errors.
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveConfiguration(@Valid @RequestBody ConfigurationDTO configDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getFieldErrors().forEach(error ->
                    errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
            );
            return ResponseEntity.badRequest().body("Validation errors: " + errors.toString());
        }
        try {
            Configuration config = modelMapper.map(configDTO, Configuration.class);
            Configuration savedConfig = configurationService.saveConfiguration(config);
            ConfigurationDTO savedConfigDTO = modelMapper.map(savedConfig, ConfigurationDTO.class);
            return ResponseEntity.ok(savedConfigDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving configuration: " + e.getMessage());
        }
    }

    /**
     * Retrieves the latest configuration.
     *
     * @return The latest configuration or a not found message.
     */
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestConfiguration() {
        Configuration config = configurationService.getLatestConfiguration();
        if (config != null) {
            ConfigurationDTO configDTO = modelMapper.map(config, ConfigurationDTO.class);
            return ResponseEntity.ok(configDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Additional endpoints for configurations if needed
}

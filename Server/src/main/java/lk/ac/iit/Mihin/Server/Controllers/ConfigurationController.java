
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
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/save")
    public ResponseEntity<?> saveConfiguration(@Valid @RequestBody ConfigurationDTO configDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getFieldErrors().forEach(error ->
                    errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
            );
            return ResponseEntity.badRequest().body("Validation errors: " + errors.toString());
        }

        Configuration config = modelMapper.map(configDTO, Configuration.class);

        // Check if maxTicketCapacity is valid
        if (!config.isValidMaxCapacity()) {
            return ResponseEntity.badRequest().body("Max Ticket Capacity cannot be less than the number of vendors or customers.");
        }

        try {
            Configuration savedConfig = configurationService.saveConfiguration(config);
            ConfigurationDTO savedConfigDTO = modelMapper.map(savedConfig, ConfigurationDTO.class);
            return ResponseEntity.ok(savedConfigDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving configuration: " + e.getMessage());
        }
    }

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

    @GetMapping("/all")
    public ResponseEntity<List<ConfigurationDTO>> getAllConfigurations() {
        List<Configuration> configs = configurationService.getAllConfigurations();
        List<ConfigurationDTO> dtos = configs.stream()
                .map(c -> modelMapper.map(c, ConfigurationDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteConfiguration(@PathVariable int id) {
        boolean deleted = configurationService.deleteConfiguration(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Configuration deleted successfully.");
    }
}

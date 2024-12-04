package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.ConfigurationDTO;
import lk.ac.iit.Mihin.Server.Model.Configuration;
import lk.ac.iit.Mihin.Server.Services.ConfigurationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ConfigurationDTO> saveConfiguration(@RequestBody ConfigurationDTO configDTO) {
        Configuration config = modelMapper.map(configDTO, Configuration.class);
        Configuration savedConfig = configurationService.saveConfiguration(config);
        ConfigurationDTO savedConfigDTO = modelMapper.map(savedConfig, ConfigurationDTO.class);
        return ResponseEntity.ok(savedConfigDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationDTO> getConfiguration(@PathVariable int id) {
        Configuration config = configurationService.getConfiguration(id);
        if (config != null) {
            ConfigurationDTO configDTO = modelMapper.map(config, ConfigurationDTO.class);
            return ResponseEntity.ok(configDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Additional endpoints for configurations
}

package lk.ac.iit.Mihin.TicketingSystem.configuration.Controller;

//new one
import lk.ac.iit.Mihin.TicketingSystem.TicketPool.TicketPool;
import lk.ac.iit.Mihin.TicketingSystem.configuration.Service.ConfigurationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lk.ac.iit.Mihin.TicketingSystem.configuration.Configuration;

@RestController
@RequestMapping("/ticketSystem")
public class ConfigurationController {
    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping
    public ResponseEntity<Configuration> getConfiguration() {
        Configuration configuration = configurationService.getConfiguration();
        return ResponseEntity.ok(configuration);
    }

    @GetMapping("/totalTickets")
    public ResponseEntity<Integer> getTotalTickets() {
        int totalTickets= configurationService.getConfiguration().getTotalTickets();
        return ResponseEntity.ok(totalTickets);
    }

    @GetMapping("/ticketReleaseRate")
    public ResponseEntity<Integer> getTicketReleaseRate() {
        int ticketReleaseRate = configurationService.getConfiguration().getTicketReleaseRate();
        return ResponseEntity.ok(ticketReleaseRate);
    }

    @GetMapping("/customerRetrievalRate")
    public ResponseEntity<Integer> getCustomerRetrievalRate() {
        int customerRetrievalRate = configurationService.getConfiguration().getCustomerRetrievalRate();
        return ResponseEntity.ok(customerRetrievalRate);
    }

    @GetMapping("/maxTicketCapacity")
    public ResponseEntity<Integer> getMaxTicketCapacity() {
        int maxTicketCapacity = configurationService.getConfiguration().getMaxTicketCapacity();
        return ResponseEntity.ok(maxTicketCapacity);
    }

    @PostMapping("/ticketPool")
    public ResponseEntity<String> addTicketPool(@RequestBody TicketPool ticketPool){
        configurationService.saveTicketPool(ticketPool);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added successfully");
    }

    @PostMapping("/totalTickets")
    public ResponseEntity<Integer>updateTotalTickets(@RequestBody int totalTickets) {
        configurationService.setTotalTickets(totalTickets);
        return ResponseEntity.status(HttpStatus.OK).body(totalTickets);
    }

    @PostMapping("/ticketReleaseRate")
    public ResponseEntity<Integer> updateTicketReleaseRate(@RequestBody int ticketReleaseRate) {
        configurationService.setTicketReleaseRate(ticketReleaseRate);
        return ResponseEntity.status(HttpStatus.OK).body(ticketReleaseRate);
    }

    @PostMapping("/customerRetrievalRate")
    public ResponseEntity<Integer> updateCustomerRetrievalRate(@RequestBody int customerRetrievalRate) {
        configurationService.setCustomerRetrievalRate(customerRetrievalRate);
        return ResponseEntity.status(HttpStatus.OK).body(customerRetrievalRate);
    }

    @PostMapping("/maxTicketCapacity")
    public ResponseEntity<Integer> updateMaxTicketCapacity(@RequestBody int maxTicketCapacity) {
        configurationService.setMaxTicketCapacity(maxTicketCapacity);
        return ResponseEntity.status(HttpStatus.OK).body(maxTicketCapacity);
    }

    @PutMapping("/configuration")
    public ResponseEntity<String> updateConfiguration(@RequestBody Configuration newConfiguration){
        configurationService.updateConfiguration(newConfiguration);
        return ResponseEntity.status(HttpStatus.OK).body("Updated configuration successfully");
    }

    @DeleteMapping("/configuration")
    public ResponseEntity<String> deleteConfiguration(){
        configurationService.resetConfiguration();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("resetting configuration to default");
    }

}

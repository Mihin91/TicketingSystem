package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "http://localhost:5173")  // React frontend URL
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<List<String>> getLogs() {
        List<String> logs = logService.getLogs();
        return ResponseEntity.ok(logs);
    }
}

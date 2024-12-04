package lk.ac.iit.Mihin.Server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// In any class where you generate logs
@Service
public class LogImplementation {

    @Autowired
    private LogService logService;

    public void performAction() {
        // Your logic here
        String logMessage = "Action performed successfully";
        logService.addLog(logMessage);
    }
}

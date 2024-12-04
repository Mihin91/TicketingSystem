package lk.ac.iit.Mihin.Server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    @Autowired
    private LogService logService;

    public void startSimulation() {
        // Simulation logic...
        logService.addLog("Simulation started.");
    }

    public void stopSimulation() {
        // Simulation logic...
        logService.addLog("Simulation stopped.");
    }
}


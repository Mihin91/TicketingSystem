// src/main/java/lk/ac/iit/Mihin/Server/Services/LogService.java
package lk.ac.iit.Mihin.Server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    private final List<String> logs = new ArrayList<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Adds a log entry and broadcasts it to the frontend
    public synchronized void addLog(String log) {
        logs.add(log);
        messagingTemplate.convertAndSend("/topic/logs", log);
        System.out.println("Sent log via WebSocket: " + log);
    }

    // Retrieves all logs
    public synchronized List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    // Clears all logs and notifies the frontend
    public synchronized void clearLogs() {
        logs.clear();
        messagingTemplate.convertAndSend("/topic/logs", "LOGS_CLEARED"); // Special message to indicate logs are cleared
        System.out.println("Logs have been cleared.");
    }
}

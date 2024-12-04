
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

    public void addLog(String log) {
        logs.add(log);
        // Send the log message to the /topic/logs destination
        messagingTemplate.convertAndSend("/topic/logs", log);
        System.out.println("Sent log via WebSocket: " + log);
    }

    public List<String> getLogs() {
        return logs;
    }
}

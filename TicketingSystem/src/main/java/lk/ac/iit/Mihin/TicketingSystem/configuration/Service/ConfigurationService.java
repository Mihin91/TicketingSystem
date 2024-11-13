package lk.ac.iit.Mihin.TicketingSystem.configuration.Service;


import jakarta.annotation.PostConstruct;
import lk.ac.iit.Mihin.TicketingSystem.TicketPool.TicketPool;
import lk.ac.iit.Mihin.TicketingSystem.Repositories.TicketPoolRepository;
import lk.ac.iit.Mihin.TicketingSystem.configuration.Configuration;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationService {
    private final TicketPoolRepository ticketPoolRepository;
    private Configuration configuration = new Configuration(100, 10, 5,
            500);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE = "TicketingSystem.json";

    public ConfigurationService(TicketPoolRepository ticketPoolRepository) {
        this.ticketPoolRepository = ticketPoolRepository;
    }

    @PostConstruct
    public void init() {
        loadConfiguration();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public TicketPool getTicketPool(Long id){
        return ticketPoolRepository.findById(id).orElseThrow(() -> new RuntimeException("TicketPool not found"));
    }

    public TicketPool saveTicketPool(TicketPool ticketPool){
        return ticketPoolRepository.save(ticketPool);
    }

    public synchronized void setTotalTickets(int totalTickets) {
        if (totalTickets > 0) {
            configuration.setTotalTickets(totalTickets);
            saveConfiguration();
        } else {
            throw new IllegalArgumentException("Total tickets containing must be greater be zero");
        }
    }

    public synchronized void setTicketReleaseRate(int ticketReleaseRate) {
        if (ticketReleaseRate > 0) {
            configuration.setTicketReleaseRate(ticketReleaseRate);
            saveConfiguration();
        } else {
            throw new IllegalArgumentException("Ticket release rate cannot be zero");
        }
    }

    public synchronized void setCustomerRetrievalRate(int customerRetrievalRate) {
        if (customerRetrievalRate > 0) {
            configuration.setCustomerRetrievalRate(customerRetrievalRate);
            saveConfiguration();
        } else {
            throw new IllegalArgumentException("Customer retrieval rate cannot be zero");
        }
    }

    public synchronized void setMaxTicketCapacity(int maxTicketCapacity) {
        if (maxTicketCapacity > 0) {
            configuration.setMaxTicketCapacity(maxTicketCapacity);
            saveConfiguration();
        } else {
            throw new IllegalArgumentException("max ticket capacity must be greater than zero");
        }
    }

    public synchronized void saveConfiguration(){
        try(FileWriter writer = new FileWriter(CONFIG_FILE)){
            gson.toJson(configuration, writer);
        } catch (IOException e){
            System.out.println("Error saving the configuration file");
        }
    }

    public synchronized void loadConfiguration(){
        File file = new File(CONFIG_FILE);
        if(!file.exists()){
            System.out.println("file does not exist");
            saveConfiguration();
            return;
        }

        try(FileReader fileReader = new FileReader(CONFIG_FILE)){
            configuration = gson.fromJson(fileReader, Configuration.class);
        } catch (IOException e){
            System.out.println("Error in file loading");
        }
    }

    public synchronized void updateConfiguration(Configuration newConfiguration) {
        if (newConfiguration != null) {
            this.configuration = newConfiguration;
            System.out.println("configuration updated :" + newConfiguration);
            saveConfiguration();
        } else {
            throw new IllegalArgumentException("Cannot be null");
        }
    }

    public synchronized void resetConfiguration(){
        this.configuration = new Configuration(100,10,5,500);
        saveConfiguration();
    }
}





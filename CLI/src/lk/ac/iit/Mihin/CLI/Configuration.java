package lk.ac.iit.Mihin.CLI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

public class Configuration {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int customerRetrievalRate;
    private final int maxTicketCapacity;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void saveToFile(String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(this, writer);
        }
    }

    public static Configuration loadFromFile(String filename) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filename)) {
            return gson.fromJson(reader, Configuration.class);
        }
    }

    public static Configuration promptForConfiguration(Scanner scanner) {
        int totalTickets = getPositiveInt(scanner, "Enter initial total tickets: ");
        int ticketReleaseRate = getPositiveInt(scanner, "Enter ticket release rate (ms): ");
        int customerRetrievalRate = getPositiveInt(scanner, "Enter customer retrieval rate (ms): ");
        int maxTicketCapacity = getPositiveInt(scanner, "Enter maximum ticket capacity: ");

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    private static int getPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (value > 0) {
                    return value;
                }
            }
            System.out.println("Please enter a positive number.");
            scanner.nextLine(); // Consume invalid input
        }
    }
}

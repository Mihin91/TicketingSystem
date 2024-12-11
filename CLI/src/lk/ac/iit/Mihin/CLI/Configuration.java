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
    private final int numberOfVendors;
    private final int numberOfCustomers;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate,
                         int maxTicketCapacity, int numberOfVendors, int numberOfCustomers) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
        this.numberOfVendors = numberOfVendors;
        this.numberOfCustomers = numberOfCustomers;
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

    public int getNumberOfVendors() {
        return numberOfVendors;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
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

    public static Configuration promptForConfiguration(Scanner input) {
        System.out.println("      ----Ticketing System-----     ");
        int totalTickets = getPositiveInt(input, "Enter total number of tickets: ");
        int ticketReleaseRate = getPositiveInt(input, "Enter ticket release rate per vendor (ms): ");
        int customerRetrievalRate = getPositiveInt(input, "Enter customer retrieval rate per customer (ms): ");
        int maxTicketCapacity = getPositiveInt(input, "Enter maximum ticket pool capacity: ");
        int numberOfVendors = getPositiveInt(input, "Enter number of vendors: ");
        int numberOfCustomers = getPositiveInt(input, "Enter number of customers: ");

        while (maxTicketCapacity < numberOfVendors || maxTicketCapacity < numberOfCustomers) {
            System.out.println("\nError: Maximum ticket capacity should be at least equal to the number of vendors and customers.");
            maxTicketCapacity = getPositiveInt(input, "Re-enter maximum ticket pool capacity: ");
        }

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate,
                maxTicketCapacity, numberOfVendors, numberOfCustomers);
    }

    public static int getPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            if (!prompt.isEmpty()) {
                System.out.print(prompt);
            }
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Value must be greater than zero. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid positive number.");
            }
        }
    }
}

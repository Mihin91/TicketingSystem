package lk.ac.iit.Mihin.CLI;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TicketingSystem {
    private Configuration configuration;
    private TicketPool ticketPool;
    private List<Thread> vendorThreads = new ArrayList<>();
    private List<Thread> customerThreads = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>(); // List of Customer objects
    private boolean isRunning = false; // Flag to track system state
    private int numVendors;
    private int numCustomers;

    public TicketingSystem() {
        super();
    }

    private void saveConfigurationToFile() {
        String json = String.format("{\n" + "  \"totalTickets\": %d,\n" + "  \"ticketReleaseRate\": %d,\n" +
                        "  \"customerRetrievalRate\": %d,\n" +
                        "  \"maxTicketCapacity\": %d\n" + "}",

                configuration.getTotalTickets(),
                configuration.getTicketReleaseRate(),
                configuration.getCustomerRetrievalRate(),
                configuration.getMaxTicketCapacity());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ticketing_system.json"))) {
            writer.write(json);
            System.out.println("Configuration saved to ticketing_system.json");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        if (!isRunning) {
            // Configuration input only needed once
            configureSystem(scanner);
            saveConfigurationToFile();
        }

        System.out.println("System ready. Enter 'start' to start, 'stop' to stop, 'display' to show ticket counts, or 'exit' to quit.");

        while (true) {
            String option = scanner.nextLine().toLowerCase();

            switch (option) {
                case "start":
                    if (isRunning) {
                        System.out.println("System is already running...");
                    } else {
                        restartThreads();
                        System.out.println("System started.");
                    }
                    break;
                case "stop":
                    stopThreads();
                    System.out.println("System stopped.");
                    break;
                case "display":
                    displayTicketCounts();
                    break;
                case "exit":
                    stopThreads();
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("Unknown command. Please enter 'start', 'stop', 'display', or 'exit'.");
            }
        }
    }

    public void displayTicketCounts() {
        int totalTicketsPurchased = 0;
        for (Customer customer : customers) {
            totalTicketsPurchased += customer.getTicketCount();
            System.out.println("Customer " + customer.getCustomerId() + " purchased " + customer.getTicketCount() + " tickets.");
        }
        System.out.println("Total tickets purchased by all customers: " + totalTicketsPurchased);
    }

    private void configureSystem(Scanner scanner) {
        int totalTickets = getPositiveInt(scanner, "Enter total tickets: ");
        int ticketReleaseRate = getPositiveInt(scanner, "Enter ticket release rate (milliseconds): ");
        int customerRetrievalRate = getPositiveInt(scanner, "Enter customer retrieval rate (milliseconds): ");
        int maxTicketCapacity = getPositiveInt(scanner, "Enter max ticket capacity: ");
        numVendors = getPositiveInt(scanner, "Enter number of vendors: ");
        numCustomers = getPositiveInt(scanner, "Enter number of customers: ");

        configuration = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        ticketPool = new TicketPool(maxTicketCapacity);
    }

    private int getPositiveInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Value must be a positive number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void restartThreads() {
        // Stop threads if running
        stopThreads();

        // Clear old threads
        vendorThreads.clear();
        customerThreads.clear();
        customers.clear();

        // Create and start new vendor threads
        for (int i = 0; i < numVendors; i++) {
            Vendor vendor = new Vendor(i + 1, configuration.getTotalTickets(), configuration.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendor);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start new customer threads
        for (int i = 0; i < numCustomers; i++) {
            Customer customer = new Customer(i + 1, configuration.getCustomerRetrievalRate(), ticketPool);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customers.add(customer);
            customerThread.start();
        }

        isRunning = true;
    }

    private void stopThreads() {
        if (!isRunning) return;

        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        isRunning = false;
    }

    public static void main(String[] args) {
        TicketingSystem ticketSystem = new TicketingSystem();
        ticketSystem.start();
    }
}

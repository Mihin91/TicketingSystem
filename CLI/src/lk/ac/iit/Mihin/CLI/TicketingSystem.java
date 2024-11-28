package lk.ac.iit.Mihin.CLI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketingSystem {
    private Configuration configuration;
    private TicketPool ticketPool;
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private boolean isRunning = false;
    private int numVendors;
    private int numCustomers;
    private static final String fileName = "ticketing_system.json";

    public void start() {
        Scanner scanner = new Scanner(System.in);

        if (promptLoadConfiguration(scanner)) {
            loadConfigurationFromFile();
        } else {
            configureSystem(scanner);
        }

        promptForVendorsAndCustomers(scanner);
        initializeTicketPool();

        mainMenu(scanner);
    }

    private boolean promptLoadConfiguration(Scanner scanner) {
        System.out.print("Do you want to load the existing configuration? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return "yes".equals(input);
    }

    private void configureSystem(Scanner scanner) {
        configuration = Configuration.promptForConfiguration(scanner);
        saveConfigurationToFile();
    }

    private void initializeTicketPool() {
        ticketPool = new TicketPool(configuration.getMaxTicketCapacity());
        System.out.println("Ticket pool initialized with capacity: " + configuration.getMaxTicketCapacity());
    }

    private void promptForVendorsAndCustomers(Scanner scanner) {
        numVendors = getPositiveInt(scanner, "Enter number of vendors: ");
        numCustomers = getPositiveInt(scanner, "Enter number of customers: ");
    }

    private void mainMenu(Scanner scanner) {
        System.out.println("--------- Real-Time Ticketing System ---------");
        System.out.println("Commands: start, stop, display, reconfigure, exit");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "start":
                    if (!isRunning) {
                        restartThreads();
                    } else {
                        System.out.println("System is already running.");
                    }
                    break;
                case "stop":
                    stopThreads();
                    break;
                case "display":
                    displayTicketCounts();
                    break;
                case "reconfigure":
                    reconfigureSystem(scanner);
                    break;
                case "exit":
                    stopThreads();
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("Unknown command. Try again.");
            }
        }
    }

    private void restartThreads() {
        stopThreads();
        initializeThreads();
        System.out.println("System started.");
    }

    private void initializeThreads() {
        for (int i = 0; i < numVendors; i++) {
            Vendor vendor = new Vendor(i + 1, configuration.getTicketReleaseRate(), 1000, ticketPool);
            Thread vendorThread = new Thread(vendor);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        for (int i = 0; i < numCustomers; i++) {
            Customer customer = new Customer(i + 1, ticketPool, configuration.getCustomerRetrievalRate());
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customers.add(customer);
            customerThread.start();
        }

        isRunning = true;
    }

    private void stopThreads() {
        vendorThreads.forEach(thread -> {
            if (thread != null) thread.interrupt();
        });

        customerThreads.forEach(thread -> {
            if (thread != null) thread.interrupt();
        });

        isRunning = false;
        System.out.println("System stopped.");
    }

    private void displayTicketCounts() {
        if (ticketPool == null) {
            System.out.println("Ticket pool not initialized.");
            return;
        }

        int totalPurchased = customers.stream().mapToInt(Customer::getTicketCount).sum();
        System.out.println("Tickets purchased: " + totalPurchased);
        System.out.println("Remaining tickets: " + ticketPool.getRemainingTickets());
        System.out.println("Tickets released by vendors: " + ticketPool.getTotalTicketsReleased());
    }

    private void reconfigureSystem(Scanner scanner) {
        stopThreads();
        configureSystem(scanner);
        promptForVendorsAndCustomers(scanner);
        initializeTicketPool();
        System.out.println("Reconfiguration complete.");
    }

    private void saveConfigurationToFile() {
        try {
            configuration.saveToFile(fileName);
            System.out.println("Configuration saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    private void loadConfigurationFromFile() {
        try {
            configuration = Configuration.loadFromFile(fileName);
            System.out.println("Configuration loaded from " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }

    private int getPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (value > 0) return value;
            }
            System.out.println("Please enter a positive number.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    public static void main(String[] args) {
        new TicketingSystem().start();
    }
}

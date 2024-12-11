package lk.ac.iit.Mihin.CLI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TicketingSystem {
    private static final String filename = "ticketing_system.json";
    private static final Map<Integer, Vendor> vendors = new HashMap<>();
    private static final Map<Integer, Customer> customers = new HashMap<>();
    private static final Map<Integer, Thread> vendorThreads = new HashMap<>();
    private static final Map<Integer, Thread> customerThreads = new HashMap<>();
    private static TicketPool ticketPool;
    private static Configuration config;
    private static boolean isRunning = false;
    private static volatile boolean exit = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("          -----Welcome to the Ticketing System Configuration-----    ");
        boolean loadFromFile = value(scanner,
                "Do you want to load configuration from (" + filename + ")? (yes/no): ");

        if (loadFromFile) {
            try {
                config = Configuration.loadFromFile(filename);
                System.out.println("Configuration loaded successfully from " + filename + ".");
            } catch (IOException e) {
                System.out.println("Failed to load configuration from " + filename + ". Please enter manually.");
                config = Configuration.promptForConfiguration(scanner);
            }
        } else {
            config = Configuration.promptForConfiguration(scanner);
            boolean saveConfig = value(scanner,
                    "Do you want to save this configuration to the (" + filename + ")? (yes/no): ");
            if (saveConfig) {
                try {
                    config.saveToFile(filename);
                    System.out.println("Configuration saved successfully to " + filename + ".");
                } catch (IOException e) {
                    System.out.println("Failed to save configuration to " + filename + ".");
                }
            }
        }

        ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        while (!exit) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "start":
                    if (isRunning) {
                        System.out.println("System is already running.");
                    } else {
                        startSystem();
                        System.out.println("Ticketing system has been started.");
                    }
                    break;
                case "stop":
                    if (!isRunning) {
                        System.out.println("System is not running.");
                    } else {
                        stopSystem();
                        System.out.println("Ticketing system has been stopped.");
                    }
                    break;
                case "display":
                    displayStatus();
                    break;
                case "reconfigure":
                    if (isRunning) {
                        System.out.println("Please stop the system before reconfiguring.");
                    } else {
                        reconfigureSystem(scanner);
                    }
                    break;
                case "exit":
                    if (isRunning) {
                        stopSystem();
                        System.out.println("Ticketing system has been stopped.");
                    }
                    exit = true;
                    System.out.println("Ticketing System has been terminated.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }

        // Ensure all threads are terminated before exiting
        waitForThreadsToFinish();
        scanner.close();
    }

    /**
     * Displays the command menu to the user.
     */
    private static void displayMenu() {
        System.out.println("\n---- Ticketing System Menu ----");
        if (!isRunning) {
            System.out.println("1. Start - Start the ticketing system.");
            System.out.println("2. Stop - Stop the ticketing system.");
            System.out.println("3. Display - Show current status of the system.");
            System.out.println("4. Reconfigure - Change system configuration.");
            System.out.println("5. Exit - Terminate the application.");
            System.out.println("Please type 'start', 'stop', 'display', 'reconfigure', or 'exit' to choose an option.");
        } else {
            // When running, restrict options to 'stop', 'display', and 'exit'
            System.out.println("1. Stop - Stop the ticketing system.");
            System.out.println("2. Display - Show current status of the system.");
            System.out.println("3. Exit - Terminate the application.");
            System.out.println("Please type 'stop', 'display', or 'exit' to choose an option.");
        }
    }

    /**
     * Prompts the user for a "yes" or "no" response and validates the input.
     *
     * @param scanner Scanner object for input
     * @param prompt  The prompt message to display
     * @return true if user inputs "yes", false if "no"
     */
    private static boolean value(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim().toLowerCase();
            if (in.equals("yes") || in.equals("y")) {
                return true;
            } else if (in.equals("no") || in.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please type 'yes'/'y' or 'no'/'n'.");
            }
        }
    }

    /**
     * Starts the vendor and customer threads based on the current configuration.
     */
    private static void startSystem() {

        for (int i = 1; i <= config.getNumberOfVendors(); i++) {
            Vendor vendor = new Vendor(i, config.getTicketReleaseRate(), ticketPool);
            vendors.put(i, vendor);
            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
            vendorThread.start();
            vendorThreads.put(i, vendorThread);
            System.out.println("[System] Vendor-" + i + " started.");
        }

        // Start Customer threads based on configuration
        for (int i = 1; i <= config.getNumberOfCustomers(); i++) {
            Customer customer = new Customer(i, config.getCustomerRetrievalRate(), ticketPool);
            customers.put(i, customer);
            Thread customerThread = new Thread(customer, "Customer-" + i);
            customerThread.start();
            customerThreads.put(i, customerThread);
            System.out.println("[System] Customer-" + i + " started.");
        }

        isRunning = true;
    }

    /**
     * Stops all vendor and customer threads gracefully.
     */
    private static void stopSystem() {
        System.out.println("\n[System] Stopping the ticketing system...");

        // Interrupt all vendor threads
        for (Thread vendorThread : vendorThreads.values()) {
            vendorThread.interrupt();
        }

        // Interrupt all customer threads
        for (Thread customerThread : customerThreads.values()) {
            customerThread.interrupt();
        }

        // Wait for all threads to finish
        waitForThreadsToFinish();

        // Clear the vendors, customers, and threads maps
        vendors.clear();
        customers.clear();
        vendorThreads.clear();
        customerThreads.clear();

        isRunning = false;

    }

    /**
     * Waits for all vendor and customer threads to finish before proceeding.
     */
    private static void waitForThreadsToFinish() {
        // Wait for vendor threads to finish
        for (Thread vendorThread : vendorThreads.values()) {
            try {
                vendorThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted while waiting for " + vendorThread.getName() + " to terminate.");
            }
        }

        // Wait for customer threads to finish
        for (Thread customerThread : customerThreads.values()) {
            try {
                customerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted while waiting for " + customerThread.getName() + " to terminate.");
            }
        }
    }

    /**
     * Displays the current status of the ticketing system.
     */
    private static void displayStatus() {
        System.out.println("\n=== Current Ticketing System Status ===");
        System.out.println("Total Tickets: " + ticketPool.getTotalTickets());
        System.out.println("Tickets Released: " + ticketPool.getTotalTicketsReleased());
        System.out.println("Tickets Purchased: " + ticketPool.getTotalTicketsPurchased());
        System.out.println("Tickets Available in Pool: " + ticketPool.getCurrentTickets());
        System.out.println("Is Pool Closed: " + ticketPool.isClosed());

        // Displays the tickets released by each vendor
        System.out.println("\n--- Tickets Released by Each Vendor ---");
        for (int vendorId : ticketPool.getAllVendorIds()) {
            int vendorTickets = ticketPool.getVendorTicketsReleased(vendorId);
            System.out.println("Vendor-" + vendorId + " Tickets Released: " + vendorTickets);
        }
    }

    /**
     * Reconfigures the system by allowing the user to input new configuration parameters.
     */
    private static void reconfigureSystem(Scanner scanner) {
        System.out.println("\n  ----- Reconfigure Ticketing System -----");
        // Prompts the user to confirm the reconfiguration
        boolean confirmReconfig = value(scanner,
                "Are you sure you want to reconfigure the system? This will reset the ticket pool. (yes/no): ");
        if (!confirmReconfig) {
            System.out.println("Reconfiguration cancelled.");
            return;
        }

        // Prompts for new configuration
        Configuration newConfig = Configuration.promptForConfiguration(scanner);

        // Saves the new configuration if wanted by user
        boolean saveConfig = value(scanner,
                "Do you want to save the new configuration to the default file (" + filename + ")? (yes/no): ");
        if (saveConfig) {
            try {
                newConfig.saveToFile(filename);
                System.out.println("Configuration saved successfully to " + filename + ".");
            } catch (IOException e) {
                System.out.println("Failed to save configuration to " + filename + ".");
            }
        }

        // Updates the static config variable
        config = newConfig;

        // Resets the TicketPool using the reset method
        ticketPool.reset(config.getMaxTicketCapacity(), config.getTotalTickets());

        System.out.println("System has been reconfigured successfully.");
    }
}

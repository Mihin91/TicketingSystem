package lk.ac.iit.Mihin.CLI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketingSystem {
    // Define the predefined configuration filename
    private static final String CONFIG_FILENAME = "ticketing_system.json";
    private static List<Thread> vendorThreads = new ArrayList<>();
    private static List<Thread> customerThreads = new ArrayList<>();
    private static TicketPool ticketPool;
    private static Configuration config;
    private static boolean isRunning = false;
    private static volatile boolean exit = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initial Configuration Setup
        System.out.println("Welcome to the Ticketing System Configuration");
        boolean loadFromFile = getYesOrNo(scanner,
                "Do you want to load configuration from the default file (" + CONFIG_FILENAME + ")? (yes/no): ");

        if (loadFromFile) {
            try {
                config = Configuration.loadFromFile(CONFIG_FILENAME);
                System.out.println("Configuration loaded successfully from " + CONFIG_FILENAME + ".");
            } catch (IOException e) {
                System.out.println("Failed to load configuration from " + CONFIG_FILENAME + ". Please enter manually.");
                config = Configuration.promptForConfiguration(scanner);
            }
        } else {
            config = Configuration.promptForConfiguration(scanner);
            boolean saveConfig = getYesOrNo(scanner,
                    "Do you want to save this configuration to the default file (" + CONFIG_FILENAME + ")? (yes/no): ");
            if (saveConfig) {
                try {
                    config.saveToFile(CONFIG_FILENAME);
                    System.out.println("Configuration saved successfully to " + CONFIG_FILENAME + ".");
                } catch (IOException e) {
                    System.out.println("Failed to save configuration to " + CONFIG_FILENAME + ".");
                }
            }
        }

        // Initialize TicketPool with maxCapacity and totalTickets
        ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        // Start the interactive menu loop with integrated monitoring
        while (!exit) {
            displayMenu();
            System.out.print("Enter your choice: ");
            System.out.println();
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

            if (isRunning && ticketPool.isClosed()
                    && ticketPool.getTotalTicketsPurchased() >= config.getTotalTickets()) {
                stopSystem();
                exit = true;
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
        System.out.println("\n=== Ticketing System Menu ===");
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
    private static boolean getYesOrNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }

    /**
     * Starts the vendor and customer threads based on the current configuration.
     */
    private static void startSystem() {
        // Create and start Vendor threads based on configuration
        for (int i = 1; i <= config.getNumberOfVendors(); i++) {
            Vendor vendor = new Vendor(i, config.getTicketReleaseRate(), config.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
            vendorThreads.add(vendorThread);
            vendorThread.start();
            System.out.println("[System] Vendor-" + i + " started.");
        }

        // Create and start Customer threads based on configuration
        for (int i = 1; i <= config.getNumberOfCustomers(); i++) {
            Customer customer = new Customer(i, config.getCustomerRetrievalRate(), ticketPool);
            Thread customerThread = new Thread(customer, "Customer-" + i);
            customerThreads.add(customerThread);
            customerThread.start();
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
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }

        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        // Wait for all threads to finish
        waitForThreadsToFinish();

        // Clear thread lists for potential restart
        vendorThreads.clear();
        customerThreads.clear();

        isRunning = false;

        System.out.println("[System] Ticketing System has been successfully shut down and terminated.");
    }

    /**
     * Waits for all vendor and customer threads to finish before proceeding.
     */
    private static void waitForThreadsToFinish() {
        for (Thread vendorThread : vendorThreads) {
            try {
                vendorThread.join();
                System.out.println("[System] " + vendorThread.getName() + " has terminated.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted while waiting for " + vendorThread.getName() + " to terminate.");
            }
        }

        for (Thread customerThread : customerThreads) {
            try {
                customerThread.join();
                System.out.println("[System] " + customerThread.getName() + " has terminated.");
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
    }

    /**
     * Reconfigures the system by allowing the user to input new configuration parameters.
     */
    private static void reconfigureSystem(Scanner scanner) {
        System.out.println("\n=== Reconfigure Ticketing System ===");
        System.out.println("Please ensure that no vendors or customers are active before reconfiguring.");

        // Prompt user to confirm reconfiguration
        boolean confirmReconfig = getYesOrNo(scanner,
                "Are you sure you want to reconfigure the system? This will stop all current operations. (yes/no): ");
        if (!confirmReconfig) {
            System.out.println("Reconfiguration cancelled.");
            return;
        }

        // Stop the system first if it's running
        if (isRunning) {
            stopSystem();
        }

        // Prompt for new configuration
        Configuration newConfig = Configuration.promptForConfiguration(scanner);

        // Save the new configuration if desired
        boolean saveConfig = getYesOrNo(scanner,
                "Do you want to save the new configuration to the default file (" + CONFIG_FILENAME + ")? (yes/no): ");
        if (saveConfig) {
            try {
                newConfig.saveToFile(CONFIG_FILENAME);
                System.out.println("Configuration saved successfully to " + CONFIG_FILENAME + ".");
            } catch (IOException e) {
                System.out.println("Failed to save configuration to " + CONFIG_FILENAME + ".");
            }
        }

        // Reinitialize TicketPool with new configuration
        ticketPool = new TicketPool(newConfig.getMaxTicketCapacity(), newConfig.getTotalTickets());

        // Update the static config variable
        config = newConfig;

        System.out.println("System has been reconfigured successfully.");
    }
}

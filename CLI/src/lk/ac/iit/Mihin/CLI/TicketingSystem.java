package lk.ac.iit.Mihin.CLI;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class TicketingSystem {
    private Configuration configuration;
    private TicketPool ticketPool;
    private List<Thread> vendorThreads = new ArrayList<>();
    private List<Thread> customerThreads = new ArrayList<>();

    public TicketingSystem() {
        super();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        int totalTickets;
        while (true) {
            try {
                System.out.print("Enter total tickets: ");
                totalTickets = Integer.parseInt(scanner.nextLine());
                if (totalTickets <= 0) {
                    System.out.println("Total tickets must be a positive number. Please enter again");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        int ticketReleaseRate;
        while (true) {
            try {
                System.out.print("Enter ticket release rate (milliseconds): ");
                ticketReleaseRate = Integer.parseInt(scanner.nextLine());
                if (ticketReleaseRate <= 0) {
                    System.out.println("Ticket release rate must be a positive number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        int customerRetrievalRate;
        while (true) {
            try {
                System.out.print("Enter customer retrieval rate (milliseconds): ");
                customerRetrievalRate = Integer.parseInt(scanner.nextLine());
                if (customerRetrievalRate <= 0) {
                    System.out.println("Customer retrieval rate must be a positive number. Please enter again");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }


        int maxTicketCapacity;
        while (true) {
            try {
                System.out.print("Enter max ticket capacity: ");
                maxTicketCapacity = Integer.parseInt(scanner.nextLine());
                if (maxTicketCapacity <= 0) {
                    System.out.println("Max ticket capacity must be a positive number.PLease enter again");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number. PLease enter again");
            }
        }

        int numVendors;
        while (true) {
            try {
                System.out.print("Enter number of vendors: ");
                numVendors = Integer.parseInt(scanner.nextLine());
                if (numVendors <= 0) {
                    System.out.println("Number of vendors must be a positive number. Please enter again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        int numCustomers;
        while (true) {
            try {
                System.out.print("Enter number of customers: ");
                numCustomers = Integer.parseInt(scanner.nextLine());
                if (numCustomers <= 0) {
                    System.out.println("Number of customers must be a positive number. Please enter again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Create configuration and ticket pool
        configuration = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        ticketPool = new TicketPool(maxTicketCapacity);

        for (int i = 0; i < numVendors; i++) {
            Thread vendorThread = new Thread(new Vendor(i + 1, totalTickets, ticketReleaseRate, ticketPool));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        for (int i = 0; i < numCustomers; i++) {
            Thread customerThread = new Thread(new Customer(i + 1, customerRetrievalRate, ticketPool));
            customerThreads.add(customerThread);
            customerThread.start();
        }



        String option;
        while (true) {
            option = scanner.nextLine().toLowerCase();

            switch (option) {
                case "start":
                    System.out.println("System is already running...");
                    break;
                case "stop":
                    stopThreads();
                    System.out.println("Stop the System.");
                    return;
                case "exit":
                    stopThreads();
                    System.out.println("Exiting the system");
                    return;
                default:
                    System.out.println("Unknown command. Please enter 'start', 'stop', or 'exit'.");
            }
        }
    }

    private void stopThreads() {
        // Interrupt all vendor and customer threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }
    }


    public static void main(String[] args) {
        TicketingSystem ticketSystem = new TicketingSystem();
        ticketSystem.start();
    }
}

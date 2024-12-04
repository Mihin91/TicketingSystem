// src/main/java/lk/ac/iit/Mihin/Server/Model/Configuration.java
package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// Generates an all-arguments constructor
@Entity
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Total Tickets is required.")
    @Min(value = 1, message = "Total Tickets must be at least 1.")
    private int totalTickets;

    @NotNull(message = "Ticket Release Rate is required.")
    @Min(value = 1, message = "Ticket Release Rate must be at least 1 ms.")
    private int ticketReleaseRate;

    @NotNull(message = "Customer Retrieval Rate is required.")
    @Min(value = 1, message = "Customer Retrieval Rate must be at least 1 ms.")
    private int customerRetrievalRate;

    @NotNull(message = "Max Ticket Capacity is required.")
    @Min(value = 1, message = "Max Ticket Capacity must be at least 1.")
    private int maxTicketCapacity;

    @NotNull(message = "Number of Vendors is required.")
    @Min(value = 1, message = "Number of Vendors must be at least 1.")
    private int numberOfVendors;

    @NotNull(message = "Number of Customers is required.")
    @Min(value = 1, message = "Number of Customers must be at least 1.")
    private int numberOfCustomers;

    @NotNull(message = "Release Interval is required.")
    @Min(value = 1, message = "Release Interval must be at least 1 ms.")
    private int releaseInterval;

    public Configuration() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull(message = "Release Interval is required.")
    @Min(value = 1, message = "Release Interval must be at least 1 ms.")
    public int getReleaseInterval() {
        return releaseInterval;
    }

    public void setReleaseInterval(@NotNull(message = "Release Interval is required.") @Min(value = 1, message = "Release Interval must be at least 1 ms.") int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    @NotNull(message = "Number of Customers is required.")
    @Min(value = 1, message = "Number of Customers must be at least 1.")
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(@NotNull(message = "Number of Customers is required.") @Min(value = 1, message = "Number of Customers must be at least 1.") int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    @NotNull(message = "Number of Vendors is required.")
    @Min(value = 1, message = "Number of Vendors must be at least 1.")
    public int getNumberOfVendors() {
        return numberOfVendors;
    }

    public void setNumberOfVendors(@NotNull(message = "Number of Vendors is required.") @Min(value = 1, message = "Number of Vendors must be at least 1.") int numberOfVendors) {
        this.numberOfVendors = numberOfVendors;
    }

    @NotNull(message = "Max Ticket Capacity is required.")
    @Min(value = 1, message = "Max Ticket Capacity must be at least 1.")
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(@NotNull(message = "Max Ticket Capacity is required.") @Min(value = 1, message = "Max Ticket Capacity must be at least 1.") int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @NotNull(message = "Customer Retrieval Rate is required.")
    @Min(value = 1, message = "Customer Retrieval Rate must be at least 1 ms.")
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(@NotNull(message = "Customer Retrieval Rate is required.") @Min(value = 1, message = "Customer Retrieval Rate must be at least 1 ms.") int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @NotNull(message = "Ticket Release Rate is required.")
    @Min(value = 1, message = "Ticket Release Rate must be at least 1 ms.")
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(@NotNull(message = "Ticket Release Rate is required.") @Min(value = 1, message = "Ticket Release Rate must be at least 1 ms.") int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @NotNull(message = "Total Tickets is required.")
    @Min(value = 1, message = "Total Tickets must be at least 1.")
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(@NotNull(message = "Total Tickets is required.") @Min(value = 1, message = "Total Tickets must be at least 1.") int totalTickets) {
        this.totalTickets = totalTickets;
    }
}

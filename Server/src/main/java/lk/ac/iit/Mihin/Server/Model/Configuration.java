package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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

    public Configuration() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getNumberOfVendors() {
        return numberOfVendors;
    }

    public void setNumberOfVendors(int numberOfVendors) {
        this.numberOfVendors = numberOfVendors;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    // Custom validation for maxTicketCapacity
    public boolean isValidMaxCapacity() {
        return this.maxTicketCapacity >= this.numberOfVendors && this.maxTicketCapacity >= this.numberOfCustomers;
    }
}

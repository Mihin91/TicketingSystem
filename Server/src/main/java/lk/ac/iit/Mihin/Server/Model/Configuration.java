// src/main/java/lk/ac/iit/Mihin/Server/Model/Configuration.java
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
    private Integer totalTickets;

    @NotNull(message = "Ticket Release Rate is required.")
    @Min(value = 1, message = "Ticket Release Rate must be at least 1 ms.")
    private Integer ticketReleaseRate;

    @NotNull(message = "Customer Retrieval Rate is required.")
    @Min(value = 1, message = "Customer Retrieval Rate must be at least 1 ms.")
    private Integer customerRetrievalRate;

    @NotNull(message = "Max Ticket Capacity is required.")
    @Min(value = 1, message = "Max Ticket Capacity must be at least 1.")
    private Integer maxTicketCapacity;

    @NotNull(message = "Number of Vendors is required.")
    @Min(value = 1, message = "Number of Vendors must be at least 1.")
    private Integer numberOfVendors;

    @NotNull(message = "Number of Customers is required.")
    @Min(value = 1, message = "Number of Customers must be at least 1.")
    private Integer numberOfCustomers;

    public Configuration() {
    }

    // Getters and Setters without annotations
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(Integer ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public Integer getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(Integer customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public Integer getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(Integer maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public Integer getNumberOfVendors() {
        return numberOfVendors;
    }

    public void setNumberOfVendors(Integer numberOfVendors) {
        this.numberOfVendors = numberOfVendors;
    }

    public Integer getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(Integer numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}

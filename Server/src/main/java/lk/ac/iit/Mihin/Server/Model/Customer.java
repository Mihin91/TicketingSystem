// src/main/java/lk/ac/iit/Mihin/Server/Model/Customer.java
package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    private Integer customerId;

    @NotNull(message = "Retrieval Interval is required.")
    @Min(value = 1, message = "Retrieval Interval must be at least 1 ms.")
    private Integer retrievalInterval;

    @OneToMany(mappedBy = "customer")
    private List<Ticket> tickets = new ArrayList<>();

    public Customer() {
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setRetrievalInterval(@NotNull(message = "Retrieval Interval is required.") @Min(value = 1, message = "Retrieval Interval must be at least 1 ms.") Integer retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}

package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    private Integer customerId;

    @NotNull
    private int retrievalInterval;

    @OneToMany(mappedBy = "customer")
    private List<Ticket> tickets = new ArrayList<>();

    public Customer() {
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setRetrievalInterval(@NotNull int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}

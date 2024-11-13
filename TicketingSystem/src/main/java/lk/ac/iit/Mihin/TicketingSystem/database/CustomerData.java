package lk.ac.iit.Mihin.TicketingSystem.database;

import jakarta.persistence.*;

@Entity
@Table(name = "customerData")
public class CustomerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int customerId;
    private int retrievalInterval;

    public CustomerData(){
        super();
    }
    public CustomerData(int customerId, int retrievalInterval) {
        super();
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
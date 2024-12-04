// src/main/java/lk/ac/iit/Mihin/Server/Model/Ticket.java
package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    private Customer customer;

    public Ticket() {
    }

    @ManyToOne
    private Vendor vendor;

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public enum TicketStatus {
        AVAILABLE,
        PURCHASED
    }

    // Manually added setters and getters if Lombok isn't working
    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }
}

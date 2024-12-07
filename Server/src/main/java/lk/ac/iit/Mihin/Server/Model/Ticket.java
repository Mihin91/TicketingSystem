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

    @ManyToOne
    private Vendor vendor;

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public enum TicketStatus {
        AVAILABLE,
        PURCHASED
    }
}

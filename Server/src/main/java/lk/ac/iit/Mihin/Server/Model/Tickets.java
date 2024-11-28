package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // e.g., "AVAILABLE", "PURCHASED"
    private Long customerId;
    private Long vendorId;


    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
}


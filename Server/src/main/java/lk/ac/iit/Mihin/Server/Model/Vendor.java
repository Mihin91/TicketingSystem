// src/main/java/lk/ac/iit/Mihin/Server/Model/Vendor.java
package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Vendor {

    @Id
    private Integer vendorId;

    @NotNull
    private int ticketReleaseRate;

    public Vendor() {
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }
}

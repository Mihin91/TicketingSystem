// src/main/java/lk/ac/iit/Mihin/Server/Model/Vendor.java
package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Vendor {

    @Id
    private Integer vendorId;

    @NotNull(message = "Ticket Release Rate is required.")
    @Min(value = 1, message = "Ticket Release Rate must be at least 1 ms.")
    private Integer ticketReleaseRate;

    public Vendor() {
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public Integer getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public void setTicketReleaseRate(@NotNull(message = "Ticket Release Rate is required.") @Min(value = 1, message = "Ticket Release Rate must be at least 1 ms.") Integer ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }
}

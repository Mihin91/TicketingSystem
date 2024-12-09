// src/main/java/lk/ac/iit/Mihin/Server/DTO/VendorDTO.java

package lk.ac.iit.Mihin.Server.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class VendorDTO {
    @NotNull(message = "Vendor ID is required.")
    @Min(value = 1, message = "Vendor ID must be at least 1.")
    private Integer vendorId;

    @NotNull(message = "Ticket Release Rate is required.")
    @Min(value = 1, message = "Ticket Release Rate must be at least 1 ms.")
    private Integer ticketReleaseRate;

    public VendorDTO() {
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(Integer ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }
}

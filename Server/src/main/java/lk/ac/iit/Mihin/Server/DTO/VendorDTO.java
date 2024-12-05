// src/main/java/lk/ac/iit/Mihin/Server/DTO/VendorDTO.java
package lk.ac.iit.Mihin.Server.DTO;

public class VendorDTO {
    private int vendorId;
    private int ticketReleaseRate;

    public VendorDTO() {
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }
}

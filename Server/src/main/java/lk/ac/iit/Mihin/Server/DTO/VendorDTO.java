// src/main/java/lk/ac/iit/Mihin/Server/DTO/VendorDTO.java
package lk.ac.iit.Mihin.Server.DTO;


public class VendorDTO {
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;

    public VendorDTO() {
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getReleaseInterval() {
        return releaseInterval;
    }

    public void setReleaseInterval(int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }
}

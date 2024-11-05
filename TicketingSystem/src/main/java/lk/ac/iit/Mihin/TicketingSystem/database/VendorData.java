package lk.ac.iit.Mihin.TicketingSystem.database;

import jakarta.persistence.*;

@Entity
@Table(name = "vendorData")
public class VendorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int vendorId;
    private int ticketsPerRelease;


    public VendorData(){
        super();
    }
    public VendorData(int vendorId, int ticketsPerRelease) {
        super();
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }
}
// src/main/java/lk/ac/iit/Mihin/Server/Model/Vendor.java
package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.util.ArrayList;
import java.util.List;

 // Generates an all-arguments constructor
@Entity
public class Vendor {

    @Id
    private Integer vendorId; // Ensure data type matches repository

     public Vendor() {
     }

     public Integer getVendorId() {
         return vendorId;
     }

     public List<Ticket> getTickets() {
         return tickets;
     }

     public void setTickets(List<Ticket> tickets) {
         this.tickets = tickets;
     }

     @NotNull
     public int getReleaseInterval() {
         return releaseInterval;
     }

     @NotNull
     public int getTicketsPerRelease() {
         return ticketsPerRelease;
     }

     public void setTicketsPerRelease(@NotNull int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }


    public void setReleaseInterval(@NotNull int releaseInterval) {
        this.releaseInterval = releaseInterval;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    @NotNull
    private int ticketsPerRelease;

    @NotNull
    private int releaseInterval;

    @OneToMany(mappedBy = "vendor")
    private List<Ticket> tickets = new ArrayList<>();
}

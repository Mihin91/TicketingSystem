package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor

@Entity
public class Vendor implements Runnable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vendorId;

    @NotNull
    private int ticketsPerRelease;

    @NotNull
    private int releaseInterval;

    @OneToMany(mappedBy = "vendor")
    private List<Tickets> tickets = new ArrayList<>();

    @Transient
    private TicketPool ticketPool;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
        this.tickets = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int ticketsToAdd = Math.min(ticketsPerRelease, ticketPool.getRemainingCapacity());
                for (int i = 0; i < ticketsToAdd; i++) {
                    String ticket = "Ticket-" + vendorId + "-" + System.currentTimeMillis();
                    ticketPool.addTicket(ticket);
                }
                Thread.sleep(releaseInterval);
            } catch (InterruptedException e) {
                System.out.println("[Vendor] " + vendorId + " thread interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

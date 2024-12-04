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
public class Customer implements Runnable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Transient
    private TicketPool ticketPool;

    @NotNull
    private int retrievalInterval;

    @OneToMany(mappedBy = "customer")
    private List<Tickets> tickets = new ArrayList<>();

    public Customer(int customerId, TicketPool ticketPool, int retrievalInterval) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
        this.tickets = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    System.out.println("[Customer] " + customerId + " purchased ticket: " + ticket);
                } else {
                    System.out.println("[Customer] " + customerId + " could not purchase a ticket. Pool is empty.");
                }
                Thread.sleep(retrievalInterval);
            } catch (InterruptedException e) {
                System.out.println("[Customer] " + customerId + " thread interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

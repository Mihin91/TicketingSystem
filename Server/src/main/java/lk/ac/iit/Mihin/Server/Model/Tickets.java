package lk.ac.iit.Mihin.Server.Model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor

@Entity
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Vendor vendor;

    private int maxCapacity;
    private int currentTickets;
    private int remainingTickets;

    public enum TicketStatus {
        AVAILABLE,
        PURCHASED
    }
}

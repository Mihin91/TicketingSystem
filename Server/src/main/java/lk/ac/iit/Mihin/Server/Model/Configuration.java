package lk.ac.iit.Mihin.Server.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor

@Entity
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int totalTickets;

    @NotNull
    private int ticketReleaseRate;

    @NotNull
    private int customerRetrievalRate;

    @NotNull
    private int maxTicketCapacity;


}

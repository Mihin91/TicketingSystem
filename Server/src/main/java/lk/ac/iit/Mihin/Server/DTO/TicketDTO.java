package lk.ac.iit.Mihin.Server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor
public class TicketDTO {
    private Long id;
    private int maxCapacity;
    private int currentTickets;
    private int remainingTickets;

}

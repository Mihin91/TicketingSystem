package lk.ac.iit.Mihin.Server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor
public class CustomerDTO {
    private int customerId;
    private int ticketCount;
    private int retrievalInterval; // Assuming you need this field

}

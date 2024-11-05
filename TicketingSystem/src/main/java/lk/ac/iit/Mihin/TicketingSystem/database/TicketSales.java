package lk.ac.iit.Mihin.TicketingSystem.database;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticketSales")
public class TicketSales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int customerId;
    private LocalDateTime saleTime;

    public TicketSales(){
        super();
    }
    public TicketSales(int customerId, LocalDateTime saleTimestamp) {
        this.customerId = customerId;
        this.saleTime = saleTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(LocalDateTime saleTime) {
        this.saleTime = saleTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}


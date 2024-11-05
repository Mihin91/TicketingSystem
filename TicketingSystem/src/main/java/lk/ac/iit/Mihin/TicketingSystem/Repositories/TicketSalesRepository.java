package lk.ac.iit.Mihin.TicketingSystem.Repositories;


import lk.ac.iit.Mihin.TicketingSystem.database.TicketSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSalesRepository extends JpaRepository<TicketSales, Long> {

}

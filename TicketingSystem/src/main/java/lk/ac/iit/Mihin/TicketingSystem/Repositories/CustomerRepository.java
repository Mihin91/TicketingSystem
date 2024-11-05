package lk.ac.iit.Mihin.TicketingSystem.Repositories;

import lk.ac.iit.Mihin.TicketingSystem.database.CustomerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerData, Long> {

}


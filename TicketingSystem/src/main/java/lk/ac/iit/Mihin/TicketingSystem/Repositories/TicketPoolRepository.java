package lk.ac.iit.Mihin.TicketingSystem.Repositories;

import lk.ac.iit.Mihin.TicketingSystem.TicketPool.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}

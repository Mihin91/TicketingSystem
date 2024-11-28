package lk.ac.iit.Mihin.Server.Repositories;

import lk.ac.iit.Mihin.Server.Model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketPool, Long> {
}

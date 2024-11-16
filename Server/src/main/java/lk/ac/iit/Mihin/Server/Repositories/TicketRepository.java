package lk.ac.iit.Mihin.Server.Repositories;


import lk.ac.iit.Mihin.Server.TicketPool.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long> {
    Tickets findFirstByStatus(String status);
    long countByStatus(String status);
}


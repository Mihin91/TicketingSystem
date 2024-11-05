package lk.ac.iit.Mihin.TicketingSystem.Repositories;


import lk.ac.iit.Mihin.TicketingSystem.database.VendorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendorData, Long> {

}

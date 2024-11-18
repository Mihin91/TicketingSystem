package lk.ac.iit.Mihin.Server.Repositories;



import lk.ac.iit.Mihin.Server.Vendor.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    // Custom query methods (if needed)
}

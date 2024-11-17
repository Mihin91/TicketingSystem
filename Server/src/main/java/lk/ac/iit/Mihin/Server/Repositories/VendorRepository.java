package lk.ac.iit.Mihin.Server.Repositories;



import lk.ac.iit.Mihin.Server.Vendor.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    // Custom query methods (if needed)
}

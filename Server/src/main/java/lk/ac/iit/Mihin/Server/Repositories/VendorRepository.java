// src/main/java/lk/ac/iit/Mihin/Server/Repositories/VendorRepository.java
package lk.ac.iit.Mihin.Server.Repositories;

import lk.ac.iit.Mihin.Server.Model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    // Additional query methods if needed
}

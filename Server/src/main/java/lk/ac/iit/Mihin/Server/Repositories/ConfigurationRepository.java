package lk.ac.iit.Mihin.Server.Repositories;

import lk.ac.iit.Mihin.Server.Model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
    // Additional query methods if needed
}

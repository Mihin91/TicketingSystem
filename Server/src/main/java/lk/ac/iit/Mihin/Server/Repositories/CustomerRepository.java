package lk.ac.iit.Mihin.Server.Repositories;


import lk.ac.iit.Mihin.Server.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

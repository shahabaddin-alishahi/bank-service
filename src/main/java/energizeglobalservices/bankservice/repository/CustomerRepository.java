package energizeglobalservices.bankservice.repository;

import energizeglobalservices.bankservice.domain.Account;
import energizeglobalservices.bankservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}

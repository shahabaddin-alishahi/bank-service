package energizeglobalservices.bankservice.repository;

import energizeglobalservices.bankservice.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

}

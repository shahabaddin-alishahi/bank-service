package energizeglobalservices.bankservice.repository;

import energizeglobalservices.bankservice.domain.Account;
import energizeglobalservices.bankservice.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Override
    Optional<Account> findById(Long accountId);

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Account save(Account account);


}

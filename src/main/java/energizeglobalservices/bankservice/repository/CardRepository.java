package energizeglobalservices.bankservice.repository;

import energizeglobalservices.bankservice.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    Optional<Card> findCardByCardNumber(String cardNumber);
}

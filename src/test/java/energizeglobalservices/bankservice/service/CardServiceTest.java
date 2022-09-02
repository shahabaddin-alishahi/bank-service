package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.exception.AmountLessThanZeroException;
import energizeglobalservices.bankservice.exception.CardPin1OrFingerPrintNotValidException;
import energizeglobalservices.bankservice.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CardServiceTest {

    @Autowired
    CardService cardService;

    @Autowired
    CardRepository cardRepository;

    @Test
    void depositOrWithdrawalAmount_AmountLessThanZeroException() {
        Assertions.assertThrows(AmountLessThanZeroException.class,
                () ->
                        cardService.depositOrWithdrawalAmount(2L, "", false,
                                "700b9ce7-ffd1-4eaf-83ea-60ddf902cd16", true, -100.00, Transaction.TransactionType.CASH_WITHDRAW));
    }

    @Test
    void depositOrWithdrawalAmount_CardPin1OrFingerPrintNotValidException() {
        Assertions.assertThrows(CardPin1OrFingerPrintNotValidException.class,
                () ->
                        cardService.depositOrWithdrawalAmount(2L, "", false,
                                "700b9ce7-f1-4eaf-83ea-60ddf902cd16", true, 100.00, Transaction.TransactionType.CASH_WITHDRAW));
    }

    @Test
    void depositOrWithdrawalAmount() {
        double withdrawalAmount =100.00;
        Assertions.assertTrue(cardService.depositOrWithdrawalAmount(2L, "", false,
                        "700b9ce7-ffd1-4eaf-83ea-60ddf902cd16", true, withdrawalAmount, Transaction.TransactionType.CASH_WITHDRAW).getAmount()
                .equals(withdrawalAmount));

    }

    @Test
    void getAccountBalance() {
    }

    @Test
    void create() {
        assertEquals(cardService.create(2L, "6104337844590845", "1234"
                        , "252003745822", 123456789, 0410,
                        true, false, false).getCardNumber()
                , cardRepository.findById(2L).get().getCardNumber());

    }
}
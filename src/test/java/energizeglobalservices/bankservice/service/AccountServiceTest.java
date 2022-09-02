package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.Account;
import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.exception.AmountLessThanZeroException;
import energizeglobalservices.bankservice.repository.AccountRepository;
import energizeglobalservices.bankservice.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void getBalance() {

        Account account = accountRepository.findById(2L).get();
        assertEquals(accountService.getBalance(2L, "700b9ce7-ffd1-4eaf-83ea-60ddf902cd16").getAmount(),
                account.getBalance());
    }

    @Test
    void save() {
    }

    @Test
    void depositOrWithdrawalAmount_withdrawal() {
        double withdrawalAmount =100.00;
        Assertions.assertTrue(accountService.depositOrWithdrawalAmount(2L,
                        "700b9ce7-ffd1-4eaf-83ea-60ddf902cd16", withdrawalAmount, Transaction.TransactionType.CASH_WITHDRAW).getAmount()
                .equals(withdrawalAmount));

    }

    @Test
    void depositOrWithdrawalAmount_AmountLessThanZeroException() {
        Assertions.assertThrows(AmountLessThanZeroException.class,
                () ->
                        accountService.depositOrWithdrawalAmount(2L,
                                "700b9ce7-ffd1-4eaf-83ea-60ddf902cd16", -100.00, Transaction.TransactionType.CASH_WITHDRAW));
    }

    @Test
    void createAccount() {
        Account account = accountService.createAccount(1L, true, 2000000.200);
        assertEquals(accountRepository.getById(account.getId()).getId(), account.getId());
    }
}
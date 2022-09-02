package energizeglobalservices.bankservice.service.impl;

import energizeglobalservices.bankservice.domain.Account;
import energizeglobalservices.bankservice.domain.Card;
import energizeglobalservices.bankservice.domain.Customer;
import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.exception.*;
import energizeglobalservices.bankservice.repository.AccountRepository;
import energizeglobalservices.bankservice.service.AccountService;
import energizeglobalservices.bankservice.service.CustomerService;
import energizeglobalservices.bankservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final CustomerService customerService;

    @Value("${energize-global-services.account.maxFailureRetryCount}")
    private int maxFailureRetryCount;

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public Account createAccount(Long customerId, boolean isEnabled, Double balance) {

        if (balance <= 0) {
            throw new NotSufficientBalanceException();
        }
        Customer customer = customerService.findById(customerId);

        return accountRepository.save(Account.builder()
                .customer(customer)
                .balance(balance)
                .isEnabled(isEnabled)
                .registrationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());
    }

    @Override
    public Transaction getBalance(Long accountId, String fingerPrint) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        checkAccountValidation(fingerPrint, account);

        UUID random = UUID.randomUUID();
        Card card = account.getCards().stream().filter(Card::isEnabled).findAny().orElse(null);

        return transactionService.save(Transaction.builder()
                .amount(account.getBalance())
                .card(card)
                .createDate(LocalDateTime.now())
                .requestType(Transaction.TransactionType.CHECK_BALANCE)
                .statusType(Transaction.TransactionStatus.SUCCEED)
                .sourceCardNumber(card != null ? card.getCardNumber() : StringUtils.EMPTY)
                .transactionKey(random.toString())
                .build());
    }

    @Transactional
    public void checkAccountValidation(String fingerPrint, Account account) {

        if (account.getFailureRetryCount() >= maxFailureRetryCount) {
            throw new MaxFailureRetryCountException();
        }
        if (!account.getCustomer().getFingerPrint().equals(fingerPrint)) {
            account.setFailureRetryCount(account.getFailureRetryCount() + 1);
            if (account.getFailureRetryCount() >= maxFailureRetryCount) {
                account.setEnabled(false);
                throw new CardPin1OrFingerPrintNotValidException();
            }
            throw new CardPin1OrFingerPrintNotValidException();
        }

        if (!account.isEnabled()) {
            throw new AccountNotActivatedException();
        }
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Transaction depositOrWithdrawalAmount(Long accountId, String fingerPrint,
                                                 double amount, Transaction.TransactionType transactionType) {
        if (amount <= 0)
            throw new AmountLessThanZeroException();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        checkAccountValidation(fingerPrint, account);

        Double balance = account.getBalance();
        if (transactionType.equals(Transaction.TransactionType.CASH_WITHDRAW)) {
            if (balance <= amount)
                throw new NotSufficientBalanceException();
        }
        account.setBalance(transactionType.equals(Transaction.TransactionType.CASH_WITHDRAW) ? (balance - amount) : (balance + amount));

        Card card = account.getCards().stream().filter(Card::isEnabled).findFirst().orElse(null);

        UUID random = UUID.randomUUID();

        accountRepository.save(account);

        return transactionService.save(Transaction.builder()
                .amount(amount)
                .card(card)
                .createDate(LocalDateTime.now())
                .requestType(transactionType)
                .statusType(Transaction.TransactionStatus.SUCCEED)
                .sourceCardNumber(card != null ? card.getCardNumber() : StringUtils.EMPTY)
                .transactionKey(random.toString())
                .build());
    }
}

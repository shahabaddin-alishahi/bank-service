package energizeglobalservices.bankservice.service.impl;

import energizeglobalservices.bankservice.domain.Account;
import energizeglobalservices.bankservice.domain.Card;
import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.exception.*;
import energizeglobalservices.bankservice.repository.CardRepository;
import energizeglobalservices.bankservice.service.AccountService;
import energizeglobalservices.bankservice.service.CardService;
import energizeglobalservices.bankservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Value("${energize-global-services.card.maxFailureRetryCount}")
    private int maxFailureRetryCount;

    @Override
    public Card getCard(String cardNumber) {
        return cardRepository.findCardByCardNumber(cardNumber).orElseThrow(CardNotFoundException::new);
    }

    @Override
    public Card create(Long accountId, String cardNumber, String pin1, String pin2,
                       int cvv2, int expireDate, Boolean isEnabled, Boolean blocked, Boolean suspended) {
        Account account = accountService.getAccount(accountId);
        return cardRepository.save(Card.builder()
                .account(account)
                .customer(account.getCustomer())
                .cardNumber(cardNumber)
                .failureRetryCount(0)
                .pin1(pin1)
                .pin2(pin2)
                .cvv2(cvv2)
                .expireDate(expireDate)
                .isEnabled(isEnabled)
                .blocked(blocked)
                .suspended(suspended)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());
    }

    @Override
    public Transaction depositOrWithdrawalAmount(Long cardId, String pin1, Boolean pin1Check,
                                                 String fingerPrint, Boolean fingerPrintCheck,
                                                 double amount, Transaction.TransactionType transactionType) {
        if (amount <= 0)
            throw new AmountLessThanZeroException();
        Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
        Account account = card.getAccount();

        checkCardValidation(pin1, pin1Check, fingerPrint, fingerPrintCheck, card);

        Double balance = account.getBalance();
        if (transactionType.equals(Transaction.TransactionType.CASH_WITHDRAW)) {
            if (balance <= amount)
                throw new NotSufficientBalanceException();
        }
        account.setBalance(transactionType.equals(Transaction.TransactionType.CASH_WITHDRAW) ? (balance - amount) : (balance + amount));

        UUID random = UUID.randomUUID();

        accountService.save(account);

        return transactionService.save(Transaction.builder()
                .amount(amount)
                .card(card)
                .createDate(LocalDateTime.now())
                .requestType(transactionType)
                .statusType(Transaction.TransactionStatus.SUCCEED)
                .sourceCardNumber(card.getCardNumber())
                .transactionKey(random.toString())
                .build());

    }

    @Override
    public Transaction getAccountBalance(Long cardId, String pin1, Boolean pin1Check, String fingerPrint, Boolean fingerPrintCheck) {
        Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);

        checkCardValidation(pin1, pin1Check, fingerPrint, fingerPrintCheck, card);
        UUID random = UUID.randomUUID();

        return transactionService.save(Transaction.builder()
                .amount(card.getAccount().getBalance())
                .card(card)
                .createDate(LocalDateTime.now())
                .requestType(Transaction.TransactionType.CHECK_BALANCE)
                .statusType(Transaction.TransactionStatus.SUCCEED)
                .sourceCardNumber(card.getCardNumber())
                .transactionKey(random.toString())
                .build());
    }

    private void checkCardValidation(String pin1, Boolean pin1Check, String fingerPrint, Boolean fingerPrintCheck, Card card) {
        if (maxFailureRetryCount <= card.getFailureRetryCount())
            throw new MaxFailureRetryCountException();

        if (!(pin1Check && fingerPrintCheck)){
            throw new CardPin1OrFingerPrintNotValidException();
        }
        if ((pin1Check && !card.getPin1().equals(pin1)) ||
                (fingerPrintCheck && !card.getAccount().getCustomer().getFingerPrint().equals(fingerPrint))) {
            card.setFailureRetryCount(card.getFailureRetryCount() + 1);

            if (card.getFailureRetryCount() >= maxFailureRetryCount) {
                card.setSuspended(true);
            }
            cardRepository.save(card);
            throw new CardPin1OrFingerPrintNotValidException();
        }
    }
}

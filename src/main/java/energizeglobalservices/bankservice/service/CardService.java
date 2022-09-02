package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.Card;
import energizeglobalservices.bankservice.domain.Transaction;

public interface CardService {

    Transaction depositOrWithdrawalAmount(Long cardId, String pin1, Boolean pin1Check,
                                          String fingerPrint, Boolean fingerPrintCheck,
                                          double amount, Transaction.TransactionType cashDeposit);

    Transaction getAccountBalance(Long cardId, String pin1, Boolean pin1Check,
                                  String fingerPrint, Boolean fingerPrintCheck);

    Card create(Long accountId, String cardNumber, String pin1, String pin2,
                int cvv2, int expireDate, Boolean isEnabled, Boolean blocked, Boolean suspended);

    Card getCard(String cardNumber);
}

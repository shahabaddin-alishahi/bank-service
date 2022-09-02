package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.Account;
import energizeglobalservices.bankservice.domain.Transaction;

public interface AccountService {

    Transaction getBalance(Long accountId, String fingerPrint);

    Account save(Account account);

    Transaction depositOrWithdrawalAmount(Long accountId, String fingerPrint, double amount, Transaction.TransactionType cashWithdraw);

    Account createAccount(Long customerId, boolean isEnabled, Double balance);

    Account getAccount(Long accountId);
}

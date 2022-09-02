package energizeglobalservices.bankservice.service.impl;

import energizeglobalservices.bankservice.domain.Transaction;
import energizeglobalservices.bankservice.repository.TransactionRepository;
import energizeglobalservices.bankservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}

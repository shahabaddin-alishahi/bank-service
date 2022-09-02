package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TransactionServiceTest {

    @MockBean
    TransactionService transactionService;

    @Test
    void save() {
        UUID random = UUID.randomUUID();
        Transaction transaction = Transaction.builder().sourceCardNumber("123").transactionKey(random.toString()).build();
        Transaction transactionResponse = Transaction.builder().transactionKey(random.toString()).build();
        Mockito.when(transactionService.save(transaction)).thenReturn(transactionResponse);
        assertEquals(transactionService.save(transaction).getTransactionKey(), random.toString());
    }
}
package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.Customer;
import energizeglobalservices.bankservice.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void createUser() {
        UUID random = UUID.randomUUID();

        Customer customer = customerService.createUser("shahabaddin", "alishahi",
                "2520037458", "+989178018636", random.toString(), true, true);

        assertEquals(customerRepository.getById(customer.getId()).getId(), customer.getId());
    }
}
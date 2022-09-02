package energizeglobalservices.bankservice.service.impl;

import energizeglobalservices.bankservice.domain.Customer;
import energizeglobalservices.bankservice.exception.CustomerNotFoundException;
import energizeglobalservices.bankservice.repository.CustomerRepository;
import energizeglobalservices.bankservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer createUser(String firstName, String lastName,
                               String nationalCode, String mobileNumber,
                               String fingerPrint, Boolean isActive, Boolean fingerPrintEnable) {

        return customerRepository.save(Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .nationalCode(nationalCode)
                .mobileNumber(mobileNumber)
                .isActive(isActive)
                .fingerPrint(fingerPrint)
                .fingerPrintEnabled(fingerPrintEnable)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build());
    }
}

package energizeglobalservices.bankservice.service;

import energizeglobalservices.bankservice.domain.Customer;

import java.util.Set;

public interface CustomerService {

    Customer createUser(String firstName, String lastName,
                        String nationalCode, String mobileNumber,
                        String fingerPrint, Boolean isActive, Boolean fingerPrintEnable);

    Customer findById(Long customerId);
}

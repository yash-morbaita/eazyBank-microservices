package com.codewithyash.accounts.service.Impl;

import com.codewithyash.accounts.constants.AccountsConstants;
import com.codewithyash.accounts.dto.AccountsDto;
import com.codewithyash.accounts.dto.CustomerDto;
import com.codewithyash.accounts.entity.Accounts;
import com.codewithyash.accounts.entity.Customer;
import com.codewithyash.accounts.exception.CustomerAlreadyExistException;
import com.codewithyash.accounts.exception.ResourceNotFoundException;
import com.codewithyash.accounts.mapper.AccountMapper;
import com.codewithyash.accounts.mapper.CustomerMapper;
import com.codewithyash.accounts.repository.AccountRepository;
import com.codewithyash.accounts.repository.CustomerRepository;
import com.codewithyash.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

//    public AccountServiceImpl(
//            @Qualifier("accountRepository") AccountRepository accountRepository,
//            @Qualifier("customerRepository") CustomerRepository customerRepository) {
//        this.accountRepository = accountRepository;
//        this.customerRepository = customerRepository;
//    }

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer newCustomer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer= customerRepository.findByMobileNumber(newCustomer.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistException("Customer Already Exist with mobile Number :" + newCustomer.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(newCustomer);

        accountRepository.save(createNewAccount(savedCustomer));

    }

    /**
     * This method return the Customer Account based on mobile Number
     *
     * @param mobileNumber Customer Mobile Number
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber", mobileNumber)
        );
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","mobileNumber", customer.getCustomerId().toString())
        );
        CustomerDto mapCustomerDto = CustomerMapper.mapToCustomerDto(new CustomerDto(),customer);
        mapCustomerDto.setAccountsDto(AccountMapper.mapToAccountDto(new AccountsDto(),accounts));

        return mapCustomerDto;

    }

    /**
     * @param customerDto Customer object
     * @return return updated status
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto!= null) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
            );

            AccountMapper.mapToAccount(accountsDto,accounts);
            accounts = accountRepository.save(accounts);

            long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId", String.valueOf(customerId))
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber customer to be deleted
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)
        );
        customerRepository.deleteById(customer.getCustomerId());
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        return true;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        long randomAccountAccount = 1000000000L + new Random().nextInt(900000000);
        accounts.setAccountNumber(randomAccountAccount);
        return accounts;

    }
}

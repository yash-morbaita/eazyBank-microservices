package com.codewithyash.accounts.service.Impl;

import com.codewithyash.accounts.constants.AccountsConstants;
import com.codewithyash.accounts.dto.AccountMessageDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private final StreamBridge streamBridge;

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

        Accounts savedAccounts = accountRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccounts,savedCustomer);

    }

    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountMessageDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        logger.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        logger.info("Is the Communication request successfully triggered ? : {}", result);
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

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber!= null) {
            Accounts accounts = accountRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException(" Account", "AccountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountRepository.save(accounts);
            isUpdated = true;
        }
        return isUpdated;
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

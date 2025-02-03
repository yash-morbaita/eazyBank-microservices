package com.codewithyash.accounts.service.Impl;

import com.codewithyash.accounts.dto.AccountsDto;
import com.codewithyash.accounts.dto.CardsDto;
import com.codewithyash.accounts.dto.CustomerDetailsDto;
import com.codewithyash.accounts.dto.LoansDto;
import com.codewithyash.accounts.entity.Accounts;
import com.codewithyash.accounts.entity.Customer;
import com.codewithyash.accounts.exception.ResourceNotFoundException;
import com.codewithyash.accounts.mapper.AccountMapper;
import com.codewithyash.accounts.mapper.CustomerMapper;
import com.codewithyash.accounts.repository.AccountRepository;
import com.codewithyash.accounts.repository.CustomerRepository;
import com.codewithyash.accounts.service.ICustomerService;
import com.codewithyash.accounts.service.client.CardsFeignClient;
import com.codewithyash.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber", mobileNumber)
        );
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","mobileNumber", customer.getCustomerId().toString())
        );


        CustomerDetailsDto customerDetailsDto =CustomerMapper.mapToCustomerDetailsDto( new CustomerDetailsDto(), customer);
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountDto(new AccountsDto(), accounts));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchAccounts(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;



    }
}

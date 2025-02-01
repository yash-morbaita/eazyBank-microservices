package com.codewithyash.accounts.mapper;

import com.codewithyash.accounts.dto.AccountsDto;
import com.codewithyash.accounts.entity.Accounts;


public class AccountMapper {

    public static Accounts mapToAccount(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

    public static AccountsDto mapToAccountDto(AccountsDto accountsDto, Accounts accounts) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }
}

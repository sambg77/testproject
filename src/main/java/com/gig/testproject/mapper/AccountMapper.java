package com.gig.testproject.mapper;

import com.gig.testproject.dto.AccountDto;
import com.gig.testproject.entity.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDto(Account account){
        return new AccountDto(account.getAccountId(), account.getCustomerId(), account.getBalance(), account.getCreatedAt());
    }

    public static Account mapToAccount(AccountDto accountDto){
        return new Account(accountDto.getCustomerId(), accountDto.getBalance(), accountDto.getCreatedAt());
    }
}

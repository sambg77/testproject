package com.gig.testproject.service;

import java.util.List;

import com.gig.testproject.dto.AccountDto;

public interface AccountService {
    AccountDto getAccountById(Long accountId);

    List<AccountDto> getAllAccounts();

    void updateBalance(Long accountId, double amount);
}

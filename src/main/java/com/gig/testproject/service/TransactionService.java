package com.gig.testproject.service;

import java.util.List;

import com.gig.testproject.dto.TransactionDto;

public interface TransactionService {
    
    TransactionDto transferBetweenAccounts(Long fromAccountId, Long toAccountId, double amount);

    TransactionDto topUp(Long accountId, double amount);

    TransactionDto getTransactionById(Long transactionId);

    List<TransactionDto> getAllTransactions();
}

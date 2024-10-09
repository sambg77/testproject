package com.gig.testproject.mapper;

import java.util.Optional;

import com.gig.testproject.dto.TransactionDto;
import com.gig.testproject.entity.Account;
import com.gig.testproject.entity.Transaction;
import com.gig.testproject.repository.AccountRepository;

public class TransactionMapper {

    //private static AccountRepository accountRepository; 

    public static TransactionDto mapToTransactionDto(Transaction transaction){
        Account fromAccount = transaction.getFromAccount();
        
        Account toAccount = transaction.getToAccount();

        if (fromAccount == null){
            return new TransactionDto(transaction.getTransactionId(), null, toAccount.getAccountId(), transaction.getAmount(), transaction.getTransactionTime(), transaction.getStatus(), transaction.getType());
        }
        
        return new TransactionDto(transaction.getTransactionId(), fromAccount.getAccountId(), toAccount.getAccountId(), transaction.getAmount(), transaction.getTransactionTime(), transaction.getStatus(), transaction.getType());
    }

    public static Transaction mapToTransaction(TransactionDto transactionDto, AccountRepository accountRepository){
        Account fromAccount;
        Account toAccount;

        fromAccount = null;
        toAccount = null;
        
        Optional<Account> fromAccountOpt = accountRepository.findById(transactionDto.getFromAccountId());
        Optional<Account> toAccountOpt = accountRepository.findById(transactionDto.getToAccountId());

        if (fromAccountOpt.isPresent()){
            fromAccount = fromAccountOpt.get();
        }

        if(toAccountOpt.isPresent()){
            toAccount = toAccountOpt.get();
        }

        return new Transaction(fromAccount, toAccount, transactionDto.getAmount(), transactionDto.getTransactionTime(), transactionDto.getStatus(), transactionDto.getType());
    }
}

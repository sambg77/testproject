package com.gig.testproject.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gig.testproject.dto.TransactionDto;
import com.gig.testproject.entity.Transaction;
import com.gig.testproject.exception.BadRequestException;
import com.gig.testproject.exception.ResourceNotFoundException;
import com.gig.testproject.mapper.TransactionMapper;
import com.gig.testproject.repository.AccountRepository;
import com.gig.testproject.repository.TransactionRepository;
import com.gig.testproject.service.AccountService;
import com.gig.testproject.service.TransactionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private AccountService accountService;

    @Override
    public TransactionDto transferBetweenAccounts(Long fromAccountId, Long toAccountId, double amount){
        if(amount<=0){
            throw new BadRequestException("Amount being transferred must be greater than 0");
        }

        if(fromAccountId.equals(toAccountId)){
            throw new BadRequestException("Cannot transfer to the same account");
        }
        
        TransactionDto transactionDto = new TransactionDto(null, fromAccountId, toAccountId, amount, new Date(), "Authorised", 2);
        
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto, accountRepository);

        try{
            accountService.getAccountById(fromAccountId);
            accountService.getAccountById(toAccountId);
        } catch(ResourceNotFoundException re){
            throw re;
        }

        try{
            accountService.updateBalance(fromAccountId, -amount);
            accountService.updateBalance(toAccountId, amount);
        } catch(BadRequestException e){
            transaction.setStatus("Failed - Balance too low");
            
            Transaction savedTransaction = transactionRepository.save(transaction);

            return TransactionMapper.mapToTransactionDto(savedTransaction);
            
        }

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionDto topUp(Long accountId, double amount){
        try {
            accountService.getAccountById(accountId);
        } catch (ResourceNotFoundException re) {
            throw re;
        }

        if(amount<=0){
            throw new BadRequestException("Amount being topped up must be greater than 0.");
        }
        
        Long fillerFromAccountId = Long.valueOf(-1);

        TransactionDto transactionDto = new TransactionDto(null, fillerFromAccountId, accountId, amount, new Date(), "Authorised", 1);

        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto, accountRepository);

        accountService.updateBalance(accountId, amount);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionDto getTransactionById(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction with ID "+transactionId+" does not exist"));

        return TransactionMapper.mapToTransactionDto(transaction);
    }

    @Override
    public List<TransactionDto> getAllTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream().map((transaction) -> TransactionMapper.mapToTransactionDto(transaction)).collect(Collectors.toList());
    }
}

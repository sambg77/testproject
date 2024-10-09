package com.gig.testproject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gig.testproject.dto.AccountDto;
import com.gig.testproject.entity.Account;
import com.gig.testproject.entity.Customer;
import com.gig.testproject.exception.BadRequestException;
import com.gig.testproject.exception.ResourceNotFoundException;
import com.gig.testproject.mapper.AccountMapper;
import com.gig.testproject.repository.AccountRepository;
import com.gig.testproject.repository.CustomerRepository;
import com.gig.testproject.repository.TransactionRepository;
import com.gig.testproject.service.AccountService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
    
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;

    @Override
    public AccountDto getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account with ID "+ accountId+" does not exist"));

        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void updateBalance(Long accountId, double amount) {
        Account accountToUpdate = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account with ID "+ accountId+" does not exist"));
        
        double currentAccountBalance = accountToUpdate.getBalance();
        
        if (currentAccountBalance + amount < 0) {
            throw new BadRequestException("Account balance too low");
        }
        
        accountToUpdate.setBalance(currentAccountBalance+amount);

        Long customerId = accountToUpdate.getCustomerId();
        Customer customerToUpdate = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with ID "+ customerId+" does not exist"));

        double currentCustomerBalance = customerToUpdate.getBalance();
        customerToUpdate.setBalance(currentCustomerBalance+amount);

        Account updatedAccountObj = accountRepository.save(accountToUpdate);
        Customer updatedCustomerObj = customerRepository.save(customerToUpdate); 
    }
}

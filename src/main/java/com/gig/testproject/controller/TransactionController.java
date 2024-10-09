package com.gig.testproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gig.testproject.dto.TransactionDto;
import com.gig.testproject.service.TransactionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private TransactionService transactionService;

    // Build Add transfer between accounts REST API
    @PostMapping("{id1}/{id2}")
    public ResponseEntity<TransactionDto> transferBetweenAccounts(@PathVariable("id1") Long fromAccountId, @PathVariable("id2") Long toAccountId, @RequestParam double amount){
        TransactionDto transactionDto = transactionService.transferBetweenAccounts(fromAccountId, toAccountId, amount);

        return ResponseEntity.ok(transactionDto);
    }

    // Build Add top up REST API
    @PostMapping("topup/{id}")
    public ResponseEntity<TransactionDto> topUp(@PathVariable("id") Long accountId, @RequestParam double amount){
        TransactionDto transactionDto = transactionService.topUp(accountId, amount);

        return ResponseEntity.ok(transactionDto);
    }

    // Build Get Transaction REST API
    @GetMapping("{id}")
    public ResponseEntity<TransactionDto> getTransactionbyId(@PathVariable("id") Long transactionId){
        TransactionDto transactionDto = transactionService.getTransactionById(transactionId);

        return ResponseEntity.ok(transactionDto);
    }

    // Build Get All Customers REST API
    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions(){
        List<TransactionDto> transactions = transactionService.getAllTransactions();

        return ResponseEntity.ok(transactions);
    }

}

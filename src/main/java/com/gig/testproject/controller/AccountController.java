package com.gig.testproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gig.testproject.dto.AccountDto;
import com.gig.testproject.service.AccountService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;

    // Build Get Account REST API
    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccountbyId(@PathVariable("id") Long accountId){
        AccountDto accountDto = accountService.getAccountById(accountId);

        return ResponseEntity.ok(accountDto);
    }

    // Build Get All Accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.getAllAccounts();

        return ResponseEntity.ok(accounts);
    } 
}

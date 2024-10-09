package com.gig.testproject.dto;

import java.util.Date;

public class AccountDto {
    private Long accountId;
    private Long customerId;
    private double balance;
    private Date createdAt;
    
    public AccountDto(Long accountId, Long customerId, double balance, Date date) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.balance = balance;
        this.createdAt = date;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomer(Long customerId) {
        this.customerId = customerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }   
}

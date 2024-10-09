package com.gig.testproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gig.testproject.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}

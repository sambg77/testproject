package com.gig.testproject.repository;

import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gig.testproject.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByCustomerId(Long customerId);
}

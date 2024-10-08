package com.gig.testproject.repository;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gig.testproject.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}

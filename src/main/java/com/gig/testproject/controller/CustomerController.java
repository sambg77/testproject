package com.gig.testproject.controller;

import java.util.List;

//import org.apache.catalina.connector.Response;
//import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gig.testproject.dto.CustomerDto;
import com.gig.testproject.service.CustomerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    // Build Add Customer REST API
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    // Build Get Customer REST API
    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getCustomerbyId(@PathVariable("id") Long customerId){
        CustomerDto customerDto = customerService.getCustomerById(customerId);

        return ResponseEntity.ok(customerDto);
    }

    // Build Get All Customers REST API
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<CustomerDto> customers = customerService.getAllCustomers();

        return ResponseEntity.ok(customers);
    }

    // Build Update Employee REST API
    @PutMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long customerId, @RequestBody CustomerDto updatedCustomer){
        CustomerDto customerDto = customerService.updateCustomer(customerId, updatedCustomer);

        return ResponseEntity.ok(customerDto);
    }

    // Build Delete Employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long customerId){
        customerService.deleteCustomer(customerId);

        return ResponseEntity.ok("Customer deleted successfully!");
    }
}

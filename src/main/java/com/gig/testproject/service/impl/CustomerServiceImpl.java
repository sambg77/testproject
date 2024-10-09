package com.gig.testproject.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gig.testproject.dto.CustomerDto;
import com.gig.testproject.entity.Account;
import com.gig.testproject.entity.Customer;
import com.gig.testproject.exception.BadRequestException;
import com.gig.testproject.exception.ResourceNotFoundException;
import com.gig.testproject.mapper.CustomerMapper;
import com.gig.testproject.repository.AccountRepository;
import com.gig.testproject.repository.CustomerRepository;
import com.gig.testproject.service.CustomerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    
    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        if(customerDto.getFirstName() == null || customerDto.getLastName() == null || customerDto.getEmail() == null){
            throw new BadRequestException("Please fill all fields");
        }

        customerDto.setEmail(customerDto.getEmail().toLowerCase());

        if(customerRepository.findByEmail(customerDto.getEmail()).isPresent()){
            throw new BadRequestException("User with this email exists already.");
        }
        
        Customer customer = CustomerMapper.mapToCustomer(customerDto);

        Customer savedCustomer = customerRepository.save(customer);

        Account account = new Account(customer.getId(), customer.getBalance(), new Date());

        Account savedAccount = accountRepository.save(account);

        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with ID "+ customerId+" does not exist"));

        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        
        return customers.stream().map((customer) -> CustomerMapper.mapToCustomerDto(customer)).collect(Collectors.toList());
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, CustomerDto updatedCustomer) {
        
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with ID "+ customerId+" does not exist"));
        
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setEmail(updatedCustomer.getEmail());

        Customer updatedCustomerObj = customerRepository.save(customer);
        
        return CustomerMapper.mapToCustomerDto(updatedCustomerObj);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with ID "+ customerId+" does not exist"));

        Account account = accountRepository.findByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Account with Customer ID "+ customerId+" does not exist"));

        accountRepository.deleteById(account.getAccountId());
        customerRepository.deleteById(customerId);
    }

}

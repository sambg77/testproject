package com.gig.testproject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gig.testproject.dto.CustomerDto;
import com.gig.testproject.entity.Customer;
import com.gig.testproject.exception.ResourceNotFoundException;
import com.gig.testproject.mapper.CustomerMapper;
import com.gig.testproject.repository.CustomerRepository;
import com.gig.testproject.service.CustomerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;
    
    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);

        Customer savedCustomer = customerRepository.save(customer);

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

        customerRepository.deleteById(customerId);
    }

}

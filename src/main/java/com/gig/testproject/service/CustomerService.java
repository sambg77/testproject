package com.gig.testproject.service;

import java.util.List;

import com.gig.testproject.dto.CustomerDto;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto getCustomerById(Long customerId);

    List<CustomerDto> getAllCustomers();

    CustomerDto updateCustomer(Long customerId, CustomerDto updatedCustomer);

    void deleteCustomer(Long customerId);
}

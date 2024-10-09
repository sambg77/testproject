package com.gig.testproject.mapper;

import com.gig.testproject.dto.CustomerDto;
import com.gig.testproject.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer){
        return new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getBalance());
    }

    public static Customer mapToCustomer(CustomerDto customerDto){
        return new Customer(customerDto.getFirstName(), customerDto.getLastName(), customerDto.getEmail(), customerDto.getBalance());
    }
}

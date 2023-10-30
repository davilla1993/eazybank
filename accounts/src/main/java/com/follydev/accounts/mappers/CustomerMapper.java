package com.follydev.accounts.mappers;

import com.follydev.accounts.dto.CustomerDto;
import com.follydev.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        return CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .build();

    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        return Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .build();
    }
}

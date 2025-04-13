package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.validations.CustomerValidation;

@Service
public class CustomerService {
    
    private CustomerRepository customerRepository;
    private CustomerValidation customerValidation;

    public CustomerService(CustomerValidation customerValidation, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerValidation = customerValidation;
    }

    public void addCustomer(Customer customer) {
        customerValidation.AddCusomerValidation(customer);
        customerRepository.save(customer);
    }
}

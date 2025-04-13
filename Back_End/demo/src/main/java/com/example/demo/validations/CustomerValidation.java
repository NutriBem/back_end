package com.example.demo.validations;

import org.springframework.stereotype.Component;
import com.example.demo.model.Customer;

@Component
public class CustomerValidation {
    
    public void AddCusomerValidation(Customer customer) {
        if(customer.getName().length() < 3)
            throw new IllegalArgumentException(customer.getName() + " tem menos de 3 caracteres");
    
    }
}

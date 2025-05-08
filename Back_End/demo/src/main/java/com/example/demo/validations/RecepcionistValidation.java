package com.example.demo.validations;

import org.springframework.stereotype.Component;

import com.example.demo.model.Recepcionist;

import jakarta.validation.ValidationException;


@Component
public class RecepcionistValidation {
    
    public void AddRecepcionistValidation(Recepcionist recepcionist){
        validatePasswordStrength(recepcionist.getPassword());
    }
    
      private void validatePasswordStrength(String password) {
        if (!password.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$")) {//falta carater especial
            throw new ValidationException("A senha deve conter pelo menos 1 letra maiúscula e 1 número");
        }
    }
}

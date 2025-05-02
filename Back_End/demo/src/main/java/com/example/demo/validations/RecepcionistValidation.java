package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;

@Component
public class RecepcionistValidation {
    
     private RecepcionistRepository recepcionistRepository;

    List<String> erros = new ArrayList<>();

    public void AddRecepcionistValidation(Recepcionist recepcionist){
        validatePasswordStrength(recepcionist.getPassword());
    }
    
      private void validatePasswordStrength(String password) {
        if(!password.matches("^(?=.*[A-Z])")){
            erros.add("Deve conter letras maiusculas");
        }
        if (!password.matches("(?=.*[@])")) {//falta carater especial (?=.*[!@#$%^&*()_+\\-=\$$\$${};':\"|,.<>?]).{8,}$") 
            erros.add("Deve conter números");
        }
        if(!password.matches("(?=.*\\\\d).{8,}$")){
            erros.add("Deve conter caraterer especial");
        }
        if(!erros.isEmpty()){
            throw new IllegalArgumentException(erros.toString());
        }
    }

}

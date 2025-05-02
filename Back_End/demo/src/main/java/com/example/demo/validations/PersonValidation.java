package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Person;
import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;

import jakarta.validation.ValidationException;

@Component
public class PersonValidation {
    
    List<String> invalidFiels = new ArrayList<>();
    private RecepcionistRepository recepcionistRepository;

    public void create(Person person) {
        // if(isNullOrEmpty(person.getEmail())) invalidFiels.add("E-mail");
        // if(isNullOrEmpty(person.getName())) invalidFiels.add("Nome");
        // if(isNullOrEmpty(person.getPassword())) invalidFiels.add("Senha");
        // if(isNullOrEmpty(person.getTelephone())) invalidFiels.add("Telefone");

        

        if(!person.getPassword().matches(".*[A-Z].*")){
            invalidFiels.add("Deve conter letras maiusculas");
        }

        System.out.println(person.getPassword());

        // if (!person.getPassword().matches("(?=.*[@])")) {//falta carater especial (?=.*[!@#$%^&*()_+\\-=\$$\$${};':\"|,.<>?]).{8,}$") 
        //     invalidFiels.add("Deve conter @");
        // }
        // if(!person.getPassword().matches("(?=.*\\\\d).{8,}$")){
        //     invalidFiels.add("Deve conter número");
        // }


        if(!invalidFiels.isEmpty()) {
            invalidFiels.remove(invalidFiels.size() - 1);
            throw new IllegalArgumentException("Campos inválidos: " + invalidFiels);  
        } 

        
    }

    public boolean isNullOrEmpty(String data) {
        return data.isEmpty() || data == null;
    }

    private void validateEmailUniqueness(String email) {
        if (recepcionistRepository.existsByEmail(email)) {
            throw new ValidationException("Email já está cadastrado");
        }
    }

    private void sanitizeTelephone(Recepcionist recepcionist) {
        String telefoneLimpo = recepcionist.getTelephone().replaceAll("[^\\d]", "");
        recepcionist.setTelephone(telefoneLimpo);
    }
}

package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.errs.TypeError;
import com.example.demo.repository.PersonRepository;

@Component
public class Validation {

    @Autowired
    PersonRepository personRepository;

    List<TypeError> invalidFiels = new ArrayList<>();

    public void isNullOrEmpty(TypeError... typeErrors) {
        clearInvalidFields(); // Limpa a lista de campos inválidos

        // Percorre o array de valor recebidos
        for (TypeError data : typeErrors) {
            if (data.value().isEmpty() || data.value() == null) {
                invalidFiels.add(data);
                System.out.println("OI");
            }
        }
        System.out.println(invalidFiels);
        showInvalidFields(); // Exibe todos os valores inválidos
    }

    public void clearInvalidFields() {
        if (!invalidFiels.isEmpty())
            invalidFiels = new ArrayList<>();
    }

    public void validatePersonUpdate(String email) {
         if (personRepository.existsByEmail(email)) {
        throw new IllegalStateException("Email já está em uso por outro usuário");
        }
    }

    // Exibe os campos inválidos tenha alguma valor na lista
    public void showInvalidFields() {
        if (!invalidFiels.isEmpty())
            throw new IllegalArgumentException("Campos inválidos: " + invalidFiels);
    }
}

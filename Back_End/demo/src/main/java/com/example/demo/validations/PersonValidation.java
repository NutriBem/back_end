package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;

@Component
public class PersonValidation {

    private PersonRepository personRepository;

    public PersonValidation(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    List<String> invalidFiels = new ArrayList<>();

    public void create(Person person) {
        if (isNullOrEmpty(person.getEmail()))
            invalidFiels.add("E-mail");
        if (isNullOrEmpty(person.getName()))
            invalidFiels.add("Nome");
        if (isNullOrEmpty(person.getPassword()))
            invalidFiels.add("Senha");
        if (isNullOrEmpty(person.getTelephone()))
            invalidFiels.add("Telefone");

        // inserir as validações de senha

        if (!invalidFiels.isEmpty())
            throw new IllegalArgumentException("Campos inválidos: " + invalidFiels);

        if (personRepository.existsByEmail(person.getEmail()))
            throw new IllegalArgumentException("E-mail já dastrado.");

    }

    public boolean isNullOrEmpty(String data) {
        return data.isEmpty() || data == null;
    }
}

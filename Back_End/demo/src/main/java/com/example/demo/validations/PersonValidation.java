package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.errs.ValidationException;
import com.example.demo.model.Nutritionist;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;

@Component
public class PersonValidation extends Validation {

    private PersonRepository personRepository;

    public PersonValidation(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void create(Person person) {
        clearInvalidFields();

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

    public Optional<Person> login(LoginRequestDto person) {
        clearInvalidFields();

        if (isNullOrEmpty(person.email()))
            invalidFiels.add("E-mail");
        if (isNullOrEmpty(person.password()))
            invalidFiels.add("Senha");

        if (!invalidFiels.isEmpty())
            throw new IllegalArgumentException("Campos inválidos: " + invalidFiels);

        var personOptional = personRepository.findByEmail(person.email());

        if (personOptional.isEmpty())
            return personOptional;

        if (personOptional.get().getPassword().equals(person.password()))
            return personOptional;

        return Optional.empty();
    }

    public void validatePasswordStrength(String password) {
        if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {//falta carater especial
            throw new jakarta.validation.ValidationException("A senha deve conter pelo menos 1 letra maiúscula e 1 número");
        }
    }

}

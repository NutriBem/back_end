package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        clearInvalidFields();
        
        if (isNullOrEmpty(person.getEmail())) invalidFiels.add("E-mail");
        if (isNullOrEmpty(person.getName())) invalidFiels.add("Nome");
        if (isNullOrEmpty(person.getPassword())) invalidFiels.add("Senha");
        if (isNullOrEmpty(person.getTelephone())) invalidFiels.add("Telefone");

        // inserir as validações de senha

        if (!invalidFiels.isEmpty())
            throw new IllegalArgumentException("Campos inválidos: " + invalidFiels);

        if (personRepository.existsByEmail(person.getEmail()))
            throw new IllegalArgumentException("E-mail já dastrado.");

    }

    public boolean existsById(UUID id) {
        return Optional.ofNullable(personRepository.findById(id)).isPresent();
    }

    public Optional<Person> login(Person person) {
        clearInvalidFields();

        if (isNullOrEmpty(person.getEmail())) invalidFiels.add("E-mail");
        if (isNullOrEmpty(person.getPassword())) invalidFiels.add("Senha");

        if (!invalidFiels.isEmpty())
            throw new IllegalArgumentException("Campos inválidos: " + invalidFiels);

        var personOptional = personRepository.findByEmail(person.getEmail());

        if (personOptional.isEmpty())
            return personOptional;

        if (personOptional.get().getPassword().equals(person.getPassword()))
            return personOptional;

        return Optional.empty();
    }

    public boolean isNullOrEmpty(String data) {
        return data.isEmpty() || data == null;
    }

    public void clearInvalidFields() {
        if(!invalidFiels.isEmpty()) invalidFiels = new ArrayList<>();
    }

}

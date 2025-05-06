package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.validations.PersonValidation;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private PersonValidation personValidation;

    public PersonService(PersonRepository personRepository, PersonValidation personValidation) {
        this.personRepository = personRepository;
        this.personValidation = personValidation;
    }

    public Optional<Person> getById(UUID id) {
        return personRepository.findById(id);
    }

    public boolean deleteById(UUID id) {

        boolean existsById = personValidation.existsById(id);

        if (existsById)
            personRepository.deleteById(id);

        return existsById;
    }

    public Optional<Person> login(Person person) {
        return personValidation.login(person);
    }
}

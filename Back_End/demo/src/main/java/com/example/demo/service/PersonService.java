package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.PersonResponseDto;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.validations.PersonValidation;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private PersonValidation personValidation;
    private PasswordEncoder passwordEncoder;

    public PersonService(
        PersonRepository personRepository, 
        PersonValidation personValidation,
        PasswordEncoder passwordEncoder
        ){
        this.personRepository = personRepository;
        this.personValidation = personValidation;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<PersonResponseDto> getById(UUID id) {
        return personRepository.findById(id).map(PersonResponseDto::fromEntity);
    }

    public boolean deleteById(UUID id) {

        boolean existsById = personRepository.existsById(id);

        if (existsById)
            personRepository.deleteById(id);

        return existsById;
    }

    public Optional<Person> login(LoginRequestDto loginRequest) {
        return personValidation.login(loginRequest, passwordEncoder);
    }

    protected <T extends Person> T createPerson(T person) {
        personValidation.validatePasswordStrength(person.getPassword());
        
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        
        return personRepository.save(person);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

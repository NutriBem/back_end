package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.PersonResponseDto;
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

    public Optional<PersonResponseDto> getById(UUID id) {
        return personRepository.findById(id).map(PersonResponseDto::fromEntity);
    }

    public boolean deleteById(UUID id) {

        boolean existsById = personRepository.existsById(id);

        if (existsById)
            personRepository.deleteById(id);

        return existsById;
    }

    public Optional<LoginResponseDto> login(LoginRequestDto person) {
        return personValidation.login(person).map(LoginResponseDto::fromEntity);
    }
}

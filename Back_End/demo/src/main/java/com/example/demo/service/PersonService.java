package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.PersonResponseDto;
import com.example.demo.model.Patient;
import com.example.demo.model.Person;
import com.example.demo.model.Recepcionist;
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
            PasswordEncoder passwordEncoder) {
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

    @Transactional
    public <T extends Person> T updatePerson(UUID id, T updatePerson) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        System.out.println(existingPerson.getName()); // Correto!

        if (existingPerson.getClass().getName().equals(Recepcionist.class.getName())) {
            throw new IllegalArgumentException("Tipo de pessoa incompatível para atualização");
        }

        // {
        // "name": "tesste",
        // "email": "testedsads6",
        // "telephone": "123457894"
        // }

        Person newPerson = updateFields(existingPerson, updatePerson);

       // personValidation.validatePersonUpdate(updatePerson); CORRIGIR

        System.out.println("NEW PERSON:" + newPerson);

        @SuppressWarnings("unchecked")
        T savedPerson = (T) personRepository.save(newPerson);

        return savedPerson;
    }

    private <T extends Person> T updateFields(T existP, T updateP) {
        existP.setName(updateP.getName());
        existP.setEmail(updateP.getEmail());
        existP.setTelephone(updateP.getTelephone());

        return existP;
    }

    public void updatePassword(UUID id, String newPassword) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        person.setPassword(passwordEncoder.encode(newPassword));
        personRepository.save(person);
    }
}

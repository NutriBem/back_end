package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.model.Person;
import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;
import com.example.demo.validations.RecepcionistValidation;

import java.util.Optional;

import com.example.demo.validations.PersonValidation;

@Service
public class RecepcionistService {
    private PersonService personService;
    private RecepcionistRepository recepcionistRepository;
    private PersonValidation personValidation;

    public RecepcionistService(
            PersonService personService,
            RecepcionistValidation recepcionistValidation,
            RecepcionistRepository recepcionistRepository,
            PersonValidation personValidation) {
        this.personService = personService;
        this.recepcionistRepository = recepcionistRepository;
        this.personValidation = personValidation;
    }

    public Recepcionist crateRecepcionist(Recepcionist recepcionist) {
        personValidation.create(recepcionist); /* <- validações genéricas */
        personValidation.validateEmailFormat(recepcionist.getEmail());

        return personService.createPerson(recepcionist);
    }

    public Optional<Recepcionist> loginRecepcionist(LoginRequestDto loginRequest) {
        Optional<Person> person = personService.login(loginRequest);

        return person.flatMap(p -> {
            if (p instanceof Recepcionist) {
                return Optional.of((Recepcionist) p);
            }
            return Optional.empty();
        });
    }

    public boolean validarSenha(String email, String senhaDigitada) {
        Recepcionist recepcionist = recepcionistRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado"));

        return personService.validatePassword(senhaDigitada, recepcionist.getPassword());
    }
}

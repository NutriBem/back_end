package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;
import com.example.demo.validations.RecepcionistValidation;

@Service
public class RecepcionistService {

    private PersonService personService;
    private RecepcionistValidation recepcionistValidation;
    private RecepcionistRepository recepcionistRepository;

    public RecepcionistService(
        PersonService personService,
        RecepcionistValidation recepcionistValidation,
        RecepcionistRepository recepcionistRepository
    ) {
        this.personService = personService;
        this.recepcionistValidation = recepcionistValidation;
        this.recepcionistRepository = recepcionistRepository;
    }

    public Recepcionist crateRecepcionist(Recepcionist recepcionist) {
        //Validações específicas de recepcionista  recepcionistValidation.AddRecepcionistValidation(recepcionist);
        
        return personService.createPerson(recepcionist);
    }

    public boolean validarSenha(String email, String senhaDigitada) {
        Recepcionist recepcionist = recepcionistRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado"));
        
        return personService.validatePassword(senhaDigitada, recepcionist.getPassword());
    }
}

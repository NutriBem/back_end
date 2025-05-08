package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;
import com.example.demo.validations.RecepcionistValidation;

@Service
public class RecepcionistService {
    
    private final RecepcionistRepository recepcionistRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public RecepcionistService(
        RecepcionistRepository recepcionistRepository,
        BCryptPasswordEncoder passwordEncoder
    ) {
        this.recepcionistRepository = recepcionistRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validarSenha(String email, String senhaDigitada) {
      
        Recepcionist recepcionist = recepcionistRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Recepcionista não encontrado"));
    
        return passwordEncoder.matches(senhaDigitada, recepcionist.getPassword());
    }

}

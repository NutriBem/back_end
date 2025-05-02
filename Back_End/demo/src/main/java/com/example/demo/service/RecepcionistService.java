package com.example.demo.service;

import org.aspectj.apache.bcel.Repository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.Recepcionist;
import com.example.demo.repository.RecepcionistRepository;
import com.example.demo.validations.RecepcionistValidation;

@Service
public class RecepcionistService {
    
    private final RecepcionistRepository recepcionistRepository;
    private RecepcionistValidation recepcionistValidation;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RecepcionistService(
        RecepcionistRepository recepcionistRepository,
        BCryptPasswordEncoder passwordEncoder
    ) {
        this.recepcionistRepository = recepcionistRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RecepcionistService(RecepcionistValidation recepcionistValidation, RecepcionistRepository recepcionistRepository){
        this.recepcionistRepository = recepcionistRepository;
        this.recepcionistValidation = recepcionistValidation;      
    }

    public void addRecepcionist(Recepcionist recepcionist){
        recepcionistValidation.AddRecepcionistValidation(recepcionist);
        genereteHash(recepcionist);
        recepcionistRepository.save(recepcionist);
    }
    //gere o hash antes de salvar
    public void genereteHash(Recepcionist recepcionist){
        String senhaHash = passwordEncoder.encode(recepcionist.getPassword());
        recepcionist.setPassword(senhaHash);
    }

   public boolean validarSenha(String email, String senhaDigitada) {
    // Lança exceção se n existi
    Recepcionist recepcionist = recepcionistRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Email não cadastrado"));

    return passwordEncoder.matches(senhaDigitada, recepcionist.getPassword());
}
}

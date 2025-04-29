package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.RecepcionistRepository;
import com.example.demo.validations.RecepcionistValidation;

@Service
public class RecepcionistService {
    
    private RecepcionistRepository recepcionistRepository;
    private RecepcionistValidation recepcionistValidation;

    public RecepcionistService(RecepcionistValidation recepcionistValidation, RecepcionistRepository recepcionistRepository){
        this.recepcionistRepository = recepcionistRepository;
        this.recepcionistValidation = recepcionistValidation;      
    }

    
}

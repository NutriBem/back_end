package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PersonService;
import com.example.demo.service.RecepcionistService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/recepcionist")
public class RecepcionistController {
    
    private RecepcionistService recepcionistService;
    private PersonService personService;
    
    public RecepcionistController(RecepcionistService recepcionistService, PersonService personService){
        this.recepcionistService = recepcionistService;
        this.personService = personService;        
    }

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody ) {
    //     // Chama a validarSenha do serviço pá ve se funfa
    // ////}
}

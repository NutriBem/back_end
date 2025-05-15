package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Recepcionist;
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Recepcionist recepcionist) {
        try {
            recepcionistService.crateRecepcionist(recepcionist);
            return ResponseEntity.ok("created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody Person person) {
        try {
            var personOptional = personService.login(person);

            if (personOptional.isPresent())
                return ResponseEntity.ok(personOptional.get());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail e/ou senha inválidos.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

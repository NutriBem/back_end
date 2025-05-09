package com.example.demo.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.PersonLoginDTO;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody PersonLoginDTO person) {
        try {
            var personOptional = personService.login(person);

            if (personOptional.isPresent())
                return ResponseEntity.ok(personOptional.get());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail e/ou senha inválidos.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        try {
            if(personService.deleteById(id))
                return ResponseEntity.ok("Sucess!");
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }
}

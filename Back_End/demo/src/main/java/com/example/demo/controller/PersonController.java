package com.example.demo.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.service.PersonService;

@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        try {

            var patientOptional = personService.getById(id);

            if (patientOptional.isPresent())
                return ResponseEntity.ok(patientOptional.get());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR:" + e.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto person) {
        try {
            var loginReponse = personService.login(person);

            if (loginReponse.isPresent())
                return ResponseEntity.ok(loginReponse.get().id());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail e/ou senha inválidos.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        try {
            if (personService.deleteById(id))
                return ResponseEntity.ok("Sucess!");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }
}

package com.example.demo.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;
import com.example.demo.service.PersonService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;
    private PersonService personService;

    public PatientController(PatientService patientService, PersonService personService) {
        this.patientService = patientService;
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Patient patient) {
        try {
            patientService.create(patient);
            return ResponseEntity.ok("created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @GetMapping("{cpf}")
    public ResponseEntity<?> getByCPF(@PathVariable("cpf") String cpf) {
        try {
            var patientOptional = patientService.getByCpf(cpf);

            if (patientOptional.isPresent())
                return ResponseEntity.ok(patientOptional.get());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(patientService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }

}

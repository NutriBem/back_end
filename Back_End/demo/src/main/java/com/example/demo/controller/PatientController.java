package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PersonCreateResponseDto;
import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Patient patient) {
        try {
            PersonCreateResponseDto responseDto = patientService.create(patient);
            return ResponseEntity.status(201).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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

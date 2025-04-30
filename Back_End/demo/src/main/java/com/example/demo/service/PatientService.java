package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import com.example.demo.validations.PatientValidation;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private PatientValidation patientValidation;
    
    public PatientService(PatientRepository patientRepository, PatientValidation patientValidation) {
        this.patientRepository = patientRepository;
        this.patientValidation = patientValidation;
    }

    public void create(Patient patient) {
        patientValidation.createValidation(patient);
        patientRepository.save(patient);
    }
}

package com.example.demo.validations;

import org.springframework.stereotype.Component;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;

@Component
public class PatientValidation {
    private PatientRepository patientRepository;

    public PatientValidation(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void create(Patient patient) {
        if (patientRepository.existsByCpf(patient.getCpf()))
            throw new IllegalArgumentException("CPF já cadastrado.");

        if (patientRepository.existsByEmail(patient.getEmail()))
            throw new IllegalArgumentException("E-mail já cadastrado.");
    }

    public boolean isNullOrEmpty(String data) {
        return data == null || data.isEmpty();
    }
}

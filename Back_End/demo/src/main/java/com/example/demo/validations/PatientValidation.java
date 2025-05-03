package com.example.demo.validations;

import java.util.Optional;

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
    }

    public Patient getByCpf(String cpf) {
        if(isNullOrEmpty(cpf))
            throw new IllegalArgumentException("Informe o CPF");
        
        Optional<Patient> patient = patientRepository.findByCpf(cpf);

        if(!patient.isPresent()) 
            throw new IllegalArgumentException("Paciente não encontrado.");

        return patient.get();
    }

    public boolean isNullOrEmpty(String data) {
        return data.isEmpty() || data == null;
    }
}

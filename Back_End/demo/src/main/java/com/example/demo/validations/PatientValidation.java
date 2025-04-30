package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;

@Component
public class PatientValidation {

    private PatientRepository patientRepository;
    List<String> invalidFiels;

    public PatientValidation(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        invalidFiels = new ArrayList<>();
    }

    public void createValidation(Patient patient) {
        
        if(isNullOrEmpty(patient.getCpf())) invalidFiels.add("CPF");
        if(isNullOrEmpty(patient.getEmail())) invalidFiels.add("E-mail");
        if(isNullOrEmpty(patient.getPassword())) invalidFiels.add("Senha");
        if(isNullOrEmpty(patient.getTelephone())) invalidFiels.add("Telefone");

        if(!invalidFiels.isEmpty())
            throw new IllegalArgumentException("Campos inválidos: " + invalidFiels);

        if(patientRepository.existsByCpf(patient.getCpf()))
            throw new IllegalArgumentException("CPF já cadastrado.");

        if(patientRepository.existsByEmail(patient.getEmail()))
            throw new IllegalArgumentException("E-mail já cadastrado.");

    }

    public boolean isNullOrEmpty(String data) {
        return data == null || data.isEmpty();
    }
}

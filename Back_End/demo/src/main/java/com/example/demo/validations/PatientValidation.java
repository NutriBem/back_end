package com.example.demo.validations;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.demo.errs.TypeError;
import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;

@Component
public class PatientValidation extends Validation {
    private PatientRepository patientRepository;

    public PatientValidation(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void create(Patient patient) {
        isNullOrEmpty(new TypeError("Informe o CPF", patient.getCpf()));

        if (patientRepository.existsByCpf(patient.getCpf()))
            throw new IllegalArgumentException("CPF já cadastrado.");
    }

    public Patient getByCpf(String cpf) {
        isNullOrEmpty(new TypeError("Informe o CPF", cpf));

        Optional<Patient> patient = patientRepository.findByCpf(cpf);

        if (!patient.isPresent())
            throw new IllegalArgumentException("Paciente não encontrado.");

        return patient.get();
    }

    public Patient getById(String id) {
        isNullOrEmpty(new TypeError("Informe o CPF", id));

        UUID idToUuid = UUID.fromString(id); 

        Optional<Patient> patient = patientRepository.findById(idToUuid);

        if (!patient.isPresent())
            throw new IllegalArgumentException("Paciente não encontrado.");

        return patient.get();
    }
}

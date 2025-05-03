package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import com.example.demo.validations.PatientValidation;
import com.example.demo.validations.PersonValidation;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private PatientValidation patientValidation;
    private PersonValidation personValidation;

    public PatientService(PatientRepository patientRepository, PatientValidation patientValidation,
            PersonValidation personValidation) {
        this.patientRepository = patientRepository;
        this.patientValidation = patientValidation;
        this.personValidation = personValidation;
    }

    public Patient create(Patient patient) {
        personValidation.create(patient); /* <- validações genéricas */
        patientValidation.create(patient); /* <- validações específicas do paciente */
        return patientRepository.save(patient);
    }

    public Patient getByCpf(String cpf) {
        return patientValidation.getByCpf(cpf);
    }

}

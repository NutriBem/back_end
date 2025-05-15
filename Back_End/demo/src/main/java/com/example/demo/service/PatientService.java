package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PersonCreateResponseDto;
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

    public PersonCreateResponseDto create(Patient patient) {
        personValidation.create(patient); /* <- validações genéricas */
        patientValidation.create(patient); /* <- validações específicas do paciente */
        return PersonCreateResponseDto.fromtEntity(patientRepository.save(patient));
    }

    public Optional<Patient> getByCpf(String cpf) {
        return patientRepository.findByCpf(cpf);
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

}

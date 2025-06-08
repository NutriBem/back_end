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

        validateCpf(patient.getCpf());
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

    private void validateCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("[0-9]{11}"))
            throw new IllegalArgumentException("Formato invalido de cpf!");

        if (cpf.matches("(\\d)\\1{10}"))
            throw new IllegalArgumentException("Não permite CPFs com números repetidos");

        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }

        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11)
            digito1 = 0;

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }

        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11)
            digito2 = 0;

        if (digito1 == Character.getNumericValue(cpf.charAt(9))
                && digito2 == Character.getNumericValue(cpf.charAt(10)) != true)
            throw new IllegalArgumentException("cpf não valido!");
    }
}

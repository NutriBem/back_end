package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {
    Optional<Patient> findByCpf(String cpf);

    Optional<Patient> findByEmail(String email);

    Boolean existsByCpf(String cpf);

    Boolean existsByEmail(String email);
}

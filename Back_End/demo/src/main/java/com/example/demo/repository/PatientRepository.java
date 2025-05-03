package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByCpf(String cpf);

    Optional<Patient> findByEmail(String email);

    Boolean existsByCpf(String cpf);

    Boolean existsByEmail(String email);
}

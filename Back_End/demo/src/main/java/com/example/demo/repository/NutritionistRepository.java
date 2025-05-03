package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Nutritionist;
import com.example.demo.model.Patient;

public interface NutritionistRepository extends JpaRepository<Nutritionist, UUID> {
    Optional<Patient> findByCrm(String crm);
}

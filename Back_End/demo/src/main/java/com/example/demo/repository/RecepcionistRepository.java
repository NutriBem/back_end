package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Recepcionist;

public interface RecepcionistRepository extends JpaRepository<Recepcionist, UUID>{
    Optional<Recepcionist> findByName(String name);
    Optional<Recepcionist> findByEmail(String email);
}

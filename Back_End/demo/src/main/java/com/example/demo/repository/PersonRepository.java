package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    boolean existsByEmail(String email);
    Optional<Person> findByEmail(String email);
    Optional<Person> findByTelephone(String telephone);
}

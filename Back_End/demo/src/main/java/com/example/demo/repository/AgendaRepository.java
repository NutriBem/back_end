package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Agenda;
import com.example.demo.model.Nutritionist;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByNutritionist(Nutritionist nutritionist);
    boolean existsById(Long id);
}

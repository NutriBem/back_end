package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Agenda;
import com.example.demo.model.Nutritionist;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByNutritionist(Nutritionist nutritionist);
    boolean existsById(Long id);
    Optional<List<Agenda>> findByLocalDateBetween(LocalDate startDate, LocalDate endDate);
}

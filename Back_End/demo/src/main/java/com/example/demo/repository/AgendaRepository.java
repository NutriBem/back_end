package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Agenda;
import com.example.demo.model.Nutritionist;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByNutritionist(Nutritionist nutritionist);
}

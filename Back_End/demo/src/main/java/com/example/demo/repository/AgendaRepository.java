package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}

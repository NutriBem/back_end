package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    //@OneToOne // Um nutriciosta por agenda (horário)
    @Column(name = "fk_nutritionist", nullable = false)
    private UUID fkNutritionist;

    @Column(name = "agenda_date", nullable = false)
    private LocalDate agendaDate;

    @Column(name = "agenda_time", nullable = false)
    private LocalTime agendaTime;

    private Boolean disponibility;
}

package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Definir o relacionamento (OneToMany)
    @Column(name = "fk_patient", nullable = false)
    private UUID fkPatient;

    // Definir o relacionamento (OneToOne)
    @Column(name = "fk_agenda", nullable = false)
    private Integer fkAgenda;

    // Definir o relacionamento (OneToMany)
    @Column(name = "fk_receptionst", nullable = true)
    private UUID fkReceptionis;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;
    
}
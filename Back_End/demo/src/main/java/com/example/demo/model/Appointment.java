package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @OneToMany
    // @JoinColumn(name = "id")
    // @Column(name = "fk_patient", nullable = false)
    // private Patient fkPatient;

    // @OneToOne
    // @JoinColumn(name = "id")
    // @Column(name = "fk_agenda", nullable = false)
    // private Agenda fkAgenda;

    // @OneToMany
    // @JoinColumn(name = "id")
    // @Column(name = "fk_receptionst", nullable = true)
    // private Recepcionist fkReceptionis;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;
    
}
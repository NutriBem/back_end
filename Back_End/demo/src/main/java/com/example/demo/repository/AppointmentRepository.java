package com.example.demo.repository;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Appointment;
import com.example.demo.model.Person;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByFkPatient(Person person);
    List<Appointment> findByFkAgendaLocalDate(LocalDate localDate);
}

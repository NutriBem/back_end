package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.model.Agenda;
import com.example.demo.model.Appointment;
import com.example.demo.model.Patient;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.validations.AppointmentValidation;

@Service
public class AppointmentService {
    AppointmentValidation appointmentValidation;
    AppointmentRepository appointmentRepository;
    AgendaRepository agendaRepository;
    PatientRepository patientRepository;

    public AppointmentService(AppointmentValidation appointmentValidation,
            AppointmentRepository appointmentRepository, AgendaRepository agendaRepository,
            PatientRepository patientRepository) {
        this.appointmentValidation = appointmentValidation;
        this.appointmentRepository = appointmentRepository;
        this.agendaRepository = agendaRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment create(AppointmentCreateRequestDto appointment) {

        System.out.println(appointment);
        
        appointmentValidation.create(appointment);

        UUID paitentToUUID = UUID.fromString(appointment.patientId());
        Optional<Agenda> agenda = agendaRepository.findById(appointment.agendaId());
        Optional<Patient> patient = patientRepository.findById(paitentToUUID);

        Appointment newAppointment = new Appointment();

        newAppointment.setFkAgenda(agenda.get());
        newAppointment.setFkPatient(patient.get());

        return appointmentRepository.save(newAppointment);
    }

}

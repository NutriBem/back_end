package com.example.demo.service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.dto.appointment.AppointmentResponseDto;
import com.example.demo.model.Agenda;
import com.example.demo.model.Appointment;
import com.example.demo.model.Patient;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.validations.AgendaValidation;
import com.example.demo.validations.AppointmentValidation;
import com.example.demo.validations.PatientValidation;

@Service
public class AppointmentService {
    AppointmentValidation appointmentValidation;
    AppointmentRepository appointmentRepository;

    PatientValidation patientValidation;
    AgendaRepository agendaRepository;
    AgendaValidation agendaValidation;

    public AppointmentService(AppointmentValidation appointmentValidation,
            AppointmentRepository appointmentRepository,
            PatientValidation patientValidation,
            AgendaRepository agendaRepository,
            AgendaValidation agendaValidation) {
        this.appointmentValidation = appointmentValidation;
        this.appointmentRepository = appointmentRepository;
        this.patientValidation = patientValidation;
        this.agendaRepository = agendaRepository;
        this.agendaValidation = agendaValidation;
    }

    public AppointmentResponseDto create(AppointmentCreateRequestDto appointment) {
        appointmentValidation.create(appointment);

        Patient patient = patientValidation.getById(appointment.patientId());
        Agenda agenda = agendaValidation.findById(appointment.agendaId());

        Appointment newAppointment = new Appointment();
        newAppointment.setFkAgenda(agenda);
        newAppointment.setFkPatient(patient);

        agenda.setDisponibility(false); // altera a disponibilidade para false
        agendaRepository.save(agenda);

        Appointment saveAppointment = appointmentRepository.save(newAppointment);

        System.out.println(saveAppointment);

        AppointmentResponseDto response = AppointmentResponseDto.fromResponseDto(saveAppointment);

        return response;
    }

    public List<AppointmentResponseDto> getAll() {
        List<AppointmentResponseDto> appointmentResponseDtos = appointmentRepository
                .findAll()
                .stream()
                .map(a -> AppointmentResponseDto.fromResponseDto(a)).collect(Collectors.toList());

        return appointmentResponseDtos;
    }
}

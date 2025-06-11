package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.dto.appointment.AppointmentResponseDto;
import com.example.demo.model.Agenda;
import com.example.demo.model.Appointment;
import com.example.demo.model.Patient;
import com.example.demo.model.Recepcionist;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.validations.AgendaValidation;
import com.example.demo.validations.AppointmentValidation;
import com.example.demo.validations.PatientValidation;
import com.example.demo.validations.RecepcionistValidation;

@Service
public class AppointmentService {
    AppointmentValidation appointmentValidation;
    AppointmentRepository appointmentRepository;

    PatientValidation patientValidation;
    AgendaRepository agendaRepository;
    AgendaValidation agendaValidation;
    RecepcionistValidation recepcionistValidation;

    public AppointmentService(AppointmentValidation appointmentValidation,
            AppointmentRepository appointmentRepository,
            PatientValidation patientValidation,
            AgendaRepository agendaRepository,
            AgendaValidation agendaValidation,
            RecepcionistValidation recepcionistValidation) {
        this.appointmentValidation = appointmentValidation;
        this.appointmentRepository = appointmentRepository;
        this.patientValidation = patientValidation;
        this.agendaRepository = agendaRepository;
        this.agendaValidation = agendaValidation;
        this.recepcionistValidation = recepcionistValidation;
    }

    public AppointmentResponseDto create(AppointmentCreateRequestDto appointment) {
        appointmentValidation.create(appointment);

        Patient patient = patientValidation.getById(appointment.patientId());
        Agenda agenda = agendaValidation.findById(appointment.agendaId());
        Recepcionist recepcionist;

        Appointment newAppointment = new Appointment();
        newAppointment.setFkAgenda(agenda);
        newAppointment.setFkPatient(patient);

        if (!appointment.receptionistId().isEmpty()) {
            recepcionist = recepcionistValidation.getById(appointment.receptionistId());
            newAppointment.setFkReceptionist(recepcionist);
        }

        agenda.setDisponibility(false); // altera a disponibilidade para false
        agendaRepository.save(agenda);

        Appointment saveAppointment = appointmentRepository.save(newAppointment);

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

    public AppointmentResponseDto getById(Long id) {
        Appointment appointment = appointmentValidation.findById(id);
        return AppointmentResponseDto.fromResponseDto(appointment);
    }

    public void deleteById(Long id) {
        appointmentValidation.deleteById(id);
    }

    // consultas do dia
    public List<AppointmentResponseDto> getAppointmentOfTheDay() {
        List<Appointment> appointments = appointmentRepository.findByFkAgendaLocalDate(LocalDate.now().plusDays(1));

        return appointments.stream()
                .map(AppointmentResponseDto::fromResponseDto)
                .collect(Collectors.toList());
    }
}

package com.example.demo.validations;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.errs.TypeError;
import com.example.demo.model.Agenda;
import com.example.demo.model.Appointment;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.AppointmentRepository;

@Component
public class AppointmentValidation extends Validation {

    private AppointmentRepository appointmentRepository;
    private AgendaRepository agendaRepository;

    public AppointmentValidation(AgendaRepository agendaRepository, AppointmentRepository appointmentRepository) {
        this.agendaRepository = agendaRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void create(AppointmentCreateRequestDto appointment) {

        isNullOrEmpty(
                new TypeError("Informe o id da agenda", appointment.agendaId().toString()),
                new TypeError("Informe o id do paciente", appointment.patientId()));

        Optional<Agenda> agendaOptional = agendaRepository.findById(appointment.agendaId());

        if (agendaOptional.isEmpty())
            throw new IllegalArgumentException("Agenda não existe");

        if (!agendaOptional.get().isDisponibility())
            throw new IllegalArgumentException("Agenda ocupada");

        // demais validações
    }

    public Appointment findById(Long id) {
        isNullOrEmpty(new TypeError("Informe o id", id.toString()));

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isEmpty())
            throw new IllegalArgumentException("Consulta não encontrada.");

        return appointment.get();
    }

    public void deleteById(Long id) {
        isNullOrEmpty(new TypeError("Informe o id", id.toString()));
    
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isEmpty())
            throw new IllegalArgumentException("Consulta não encontrada.");

        Agenda agenda = agendaRepository.findById(appointment.get().getFkAgenda().getId()).get();
        agenda.setDisponibility(true); // Altera a disponibilidade para true

        appointmentRepository.delete(appointment.get());
    }
}

package com.example.demo.validations;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.errs.TypeError;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;

@Component
public class AppointmentValidation extends Validation {

    private AgendaRepository agendaRepository;

    public AppointmentValidation(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public void create(AppointmentCreateRequestDto appointment) {

        isNullOrEmpty(
                new TypeError("Informe o id da agenda", appointment.agendaId().toString()),
                new TypeError("Informe o id do paciente", appointment.patientId()));
           
        Optional<Agenda> agendaOptional = agendaRepository.findById(appointment.agendaId());

        if(agendaOptional.isEmpty())
            throw new IllegalArgumentException("Agenda não existe");

        if(!agendaOptional.get().isDisponibility())
            throw new IllegalArgumentException("Agenda ocupada");

        // demais validações
    }
}

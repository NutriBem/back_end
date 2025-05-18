package com.example.demo.validations;

import org.springframework.stereotype.Component;

import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.errs.TypeError;

@Component
public class AppointmentValidation extends Validation {

    public void create(AppointmentCreateRequestDto appointment) {

        isNullOrEmpty(
                new TypeError("Informe o id da agenda", appointment.agendaId().toString()),
                new TypeError("Informe o id do paciente", appointment.patientId()));

        // clearInvalidFields();
        // if(isNullOrEmpty(appointment.agendaId().toString()))
        // invalidFiels.add("Informe o id da agenda.");
        // if(isNullOrEmpty(appointment.patientId())) invalidFiels.add("Informe o id do
        // paciente.");
        // showInvalidFields();

        // demais validações
    }
}

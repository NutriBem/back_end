package com.example.demo.dto.appointment;

import java.util.UUID;

public record AppointmentCreateRequestDto(String patientId, Long agendaId, String receptionistId) {
    public static AppointmentCreateRequestDto fromEntity(String patientId, Long agendaId, String receptionistId) {
        return new AppointmentCreateRequestDto(patientId, agendaId, receptionistId);
    }
}

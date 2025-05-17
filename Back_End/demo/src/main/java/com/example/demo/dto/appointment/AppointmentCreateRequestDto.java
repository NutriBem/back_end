package com.example.demo.dto.appointment;

import java.util.UUID;

public record AppointmentCreateRequestDto(UUID patientId, UUID agendaId, UUID recepcionistId) {
    public static AppointmentCreateRequestDto fromEntity(UUID patientId, UUID agedaId, UUID recepcionistId) {
        return new AppointmentCreateRequestDto(patientId, agedaId, recepcionistId);
    }
}

package com.example.demo.dto.appointment;

public record AppointmentCreateRequestDto(String patientId, Long agendaId, String receptionistId) {
    public static AppointmentCreateRequestDto fromEntity(String patientId, Long agendaId, String receptionistId) {
        return new AppointmentCreateRequestDto(patientId, agendaId, receptionistId);
    }
}

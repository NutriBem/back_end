package com.example.demo.dto.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.demo.model.Appointment;

public record AppointmentResponseDto(Long id, String cpf, String crm, LocalDate appointmentDate,
        LocalTime appointmentTime, LocalDateTime creationDateTime) {
    public static AppointmentResponseDto fromResponseDto(Appointment appointment) {
        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getFkPatient().getCpf(),
                appointment.getFkAgenda().getNutritionist().getCrm(),
                appointment.getFkAgenda().getLocalDate(),
                appointment.getFkAgenda().getLocalTime(),
                appointment.getAppointmentDate());
    }

}

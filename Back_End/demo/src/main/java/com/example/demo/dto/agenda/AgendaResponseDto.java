package com.example.demo.dto.agenda;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.model.Agenda;

public record AgendaResponseDto(long id, String nutriName, LocalDate date, LocalTime time, boolean disponibility) {

    public static AgendaResponseDto fromEntity(Agenda agenda) {
        return new AgendaResponseDto(
            agenda.getId(),
            agenda.getNutritionist().getName(),
            agenda.getLocalDate(),
            agenda.getLocalTime(),
            agenda.isDisponibility());
    }
}

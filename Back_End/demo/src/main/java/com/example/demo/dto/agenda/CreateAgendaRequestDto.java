package com.example.demo.dto.agenda;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateAgendaRequestDto(String crm, LocalDate localDate, LocalTime localTime) {
    public static CreateAgendaRequestDto fromtEntity(String crm, LocalDate localDate, LocalTime localTime) {
        return new CreateAgendaRequestDto(
                crm,
                localDate,
                localTime);
    }
}

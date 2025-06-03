package com.example.demo.dto.agenda;

import java.time.LocalDate;

public record AgendaByDatesRequestDto(LocalDate startDate, LocalDate endDate) {
    public static AgendaByDatesRequestDto fromEntity(LocalDate startDate, LocalDate endDate) {
        return new AgendaByDatesRequestDto(startDate, endDate);
    }
}

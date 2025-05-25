package com.example.demo.validations;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.dto.agenda.CreateAgendaRequestDto;
import com.example.demo.model.Agenda;
import com.example.demo.model.Nutritionist;
import com.example.demo.repository.AgendaRepository;

@Component
public class AgendaValidation extends Validation {

    private AgendaRepository agendaRepository;

    private AgendaValidation(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public void create(CreateAgendaRequestDto data, Nutritionist nutritionist) {
        List<Agenda> agendaByNutritionist = agendaRepository.findByNutritionist(nutritionist);

        for (Agenda agenda : agendaByNutritionist) {

            if (data.localDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Data inválida.");
            }

            if (data.localDate().isEqual(LocalDate.now())) {
                if (data.localTime().isBefore(LocalTime.now())) {
                    throw new IllegalArgumentException("Horário inválido.");
                }
            }

            if (agenda.getLocalDate().equals(data.localDate())) {
                Duration between = Duration.between(agenda.getLocalTime(), data.localTime());

                Double betweentToHours = (double) between.toHours();

                if ((betweentToHours < 1 && betweentToHours > 0) || (betweentToHours > -1 && betweentToHours < 0)
                        || betweentToHours == 0)
                    throw new IllegalArgumentException("Horário não disponível.");
            }
        }
    }

    public Agenda findById(Long id) {
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isEmpty())
            throw new IllegalArgumentException("Agenda não encontrada");

        return agendaOptional.get();
    }
}

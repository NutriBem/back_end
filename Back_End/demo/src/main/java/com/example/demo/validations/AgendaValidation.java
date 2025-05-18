package com.example.demo.validations;

import java.time.Duration;
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

        if(agendaOptional.isEmpty())
            throw new IllegalArgumentException("Agenda não encontrada");

        return agendaOptional.get();
    }
}

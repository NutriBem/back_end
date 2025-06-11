package com.example.demo.scheduled;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.Agenda;
import com.example.demo.model.Nutritionist;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.NutritionistRepository;
import com.example.demo.singleton.BussinesHours;

@Component
public class ScheduledAgenda {

    private NutritionistRepository nutritionistRepository;
    private AgendaRepository agendaRepository;

    public ScheduledAgenda(NutritionistRepository nutritionistRepository, AgendaRepository agendaRepository) {
        this.nutritionistRepository = nutritionistRepository;
        this.agendaRepository = agendaRepository;
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void createAgenda() {
        List<Nutritionist> nutritionists = nutritionistRepository.findAll();

        // Horário de funcionamento da clínica
        List<LocalTime> openingHours = BussinesHours.hours();

        for (Nutritionist nutritionist : nutritionists) {
            List<Agenda> agendas = agendaRepository.findByNutritionist(nutritionist);

            long days = agendas.stream()
                    .map(Agenda::getLocalDate)
                    .distinct()
                    .count();

            byte schedulingDaysToBeCreated = (byte) (14 - days);

            if (schedulingDaysToBeCreated > 0) {
                Optional<LocalDate> lastDayOptional = agendas.stream()
                        .map(Agenda::getLocalDate)
                        .max(Comparator.naturalOrder());

                LocalDate lastDay = lastDayOptional.orElse(LocalDate.now());

                List<Agenda> novasAgendas = new ArrayList<>();

                for (byte i = 1; i <= schedulingDaysToBeCreated; i++) {
                    LocalDate novaData = lastDay.plusDays(i);
                   for (LocalTime localTime : openingHours) {
                        Agenda agenda = new Agenda();
                        agenda.setNutritionist(nutritionist);
                        agenda.setLocalDate(novaData);
                        agenda.setLocalTime(localTime);
                        novasAgendas.add(agenda);
                    }
                }

                agendaRepository.saveAll(novasAgendas);
            }
        }
    }
}
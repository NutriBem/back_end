package com.example.demo.scheduled;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @Scheduled(cron = "* * 23 * * *")
    public void createAgenda() {

        List<Nutritionist> nutritionists = nutritionistRepository.findAll();

        // horário de funcionamento da clínica
        List<LocalTime> openingHours = BussinesHours.hours();
        for (Nutritionist nutritionist : nutritionists) {
            List<Agenda> agendas = agendaRepository.findByNutritionist(nutritionist);

            long days = agendas.stream() // 
                .map(Agenda::getLocalDate)
                .distinct()
                .count();

            // duas semanas de agendas criadas
            byte schedulingDaysToBeCreateddays = (byte) (14 - days);

            if (schedulingDaysToBeCreateddays > 0) {
                LocalDate lastDay = agendas.get(agendas.size() - 1).getLocalDate(); // pega a última agenda criada
                for (byte i = 0; i < schedulingDaysToBeCreateddays; i++) {
                    for (LocalTime localTime : openingHours) {
                        Agenda agenda = new Agenda();
                        agenda.setNutritionist(nutritionist);
                        agenda.setLocalDate(lastDay.plusDays(i)); // incrementa +1 dia a partir do último
                        agenda.setLocalTime(localTime); // todos os horários do dia

                        agendaRepository.save(agenda);
                    }
                }
            }

        }
    }
}
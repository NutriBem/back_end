package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.agenda.AgendaResponseDto;
import com.example.demo.dto.agenda.CreateAgendaRequestDto;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.NutritionistRepository;
import com.example.demo.singleton.BussinesHours;
import com.example.demo.validations.NutritionistValidation;

@Service
public class AgendaService {
    private AgendaRepository agendaRepository;
    private NutritionistValidation nutritionistValidation;
    private NutritionistRepository nutritionistRepository;

    public AgendaService(AgendaRepository agendaRepository, NutritionistValidation nutritionistValidation, NutritionistRepository nutritionistRepository) {
        this.agendaRepository = agendaRepository;
        this.nutritionistValidation = nutritionistValidation;
        this.nutritionistRepository = nutritionistRepository;
    }

    // cria a agenda para as próximas duas semanas
    public void create(UUID id) {
        var nutriotionistOpt = nutritionistRepository.findById(id);

        if (nutriotionistOpt.isEmpty())
            throw new IllegalArgumentException("Nutricionista não encontrado.");

        List<Agenda> agendaNutritionist = agendaRepository.findByNutritionist(nutriotionistOpt.get());

        // criar as agendas na primeira seção do nutritionista
        if (agendaNutritionist.isEmpty()) {
            byte countDays = 0;
            LocalDate date;

            // horário de funcionamento da clínica
            List<LocalTime> openingHours = BussinesHours.hours();

            while (countDays <= 14) {
                countDays++;
                date = LocalDate.now().plusDays(countDays);

                // Pula sábados e domingos
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
                    continue;

                for (LocalTime time : openingHours) {
                    Agenda agenda = new Agenda();
                    agenda.setNutritionist(nutriotionistOpt.get());
                    agenda.setLocalDate(date);
                    agenda.setLocalTime(time);
                    agendaRepository.save(agenda);
                }
            }
            return;
        }
        
    }

    public List<AgendaResponseDto> getAgendaByCrmNutritionist(String id) {
        var nutricionistOpt = nutritionistValidation.findByCrm(id);

        if (nutricionistOpt.isEmpty())
            throw new IllegalArgumentException("Nutricionista não encontrado");

        List<Agenda> agendas = agendaRepository.findByNutritionist(nutricionistOpt.get());

        // Irei reaproveitar esse método depois
        List<AgendaResponseDto> agendaResponseDtos = agendas.stream().map(a -> new AgendaResponseDto(
                a.getNutritionist().getName(),
                a.getLocalDate(),
                a.getLocalTime(),
                a.isDisponibility())).collect(Collectors.toList());

        return agendaResponseDtos;
    }

    public List<AgendaResponseDto> getAll() {
        List<Agenda> agendas = agendaRepository.findAll();

        List<AgendaResponseDto> agendaResponseDtos = agendas.stream()
                .map(a -> new AgendaResponseDto(
                        a.getNutritionist().getName(),
                        a.getLocalDate(),
                        a.getLocalTime(),
                        a.isDisponibility()))
                .collect(Collectors.toList());

        return agendaResponseDtos;
    }
}

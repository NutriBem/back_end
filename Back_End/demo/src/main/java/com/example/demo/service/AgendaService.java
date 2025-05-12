package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.agenda.CreateAgendaRequestDto;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.validations.NutritionistValidation;

@Service
public class AgendaService {
    private AgendaRepository agendaRepository;
    private NutritionistValidation nutritionistValidation;

    public AgendaService(AgendaRepository agendaRepository, NutritionistValidation nutritionistValidation) {
        this.agendaRepository = agendaRepository;
        this.nutritionistValidation = nutritionistValidation;
    }

    public void create(CreateAgendaRequestDto data) {
        nutritionistValidation.clearInvalidFields();
        var nutriotionistOpt = nutritionistValidation.findByCrm(data.crm());

        if (nutriotionistOpt.isEmpty())
            throw new IllegalArgumentException("Médico não encontrado.");

        // implement validation from agenda

        Agenda agenda = new Agenda();
        agenda.setLocalDate(data.localDate());
        agenda.setLocalTime(data.localTime());
        agenda.setNutritionist(nutriotionistOpt.get());

        agendaRepository.save(agenda);
    }

}

package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.agenda.AgendaResponseDto;
import com.example.demo.dto.agenda.CreateAgendaRequestDto;
import com.example.demo.model.Agenda;
import com.example.demo.model.Nutritionist;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.validations.AgendaValidation;
import com.example.demo.validations.NutritionistValidation;

@Service
public class AgendaService {
    private AgendaRepository agendaRepository;
    private NutritionistValidation nutritionistValidation;
    private AgendaValidation agendaValidation;

    public AgendaService(AgendaRepository agendaRepository, NutritionistValidation nutritionistValidation,
            AgendaValidation agendaValidation) {
        this.agendaRepository = agendaRepository;
        this.nutritionistValidation = nutritionistValidation;
        this.agendaValidation = agendaValidation;
    }

    public CreateAgendaRequestDto create(CreateAgendaRequestDto data) {
        var nutriotionistOpt = nutritionistValidation.findByCrm(data.crm());

        if (nutriotionistOpt.isEmpty())
            throw new IllegalArgumentException("Médico não encontrado.");

        agendaValidation.create(data, nutriotionistOpt.get());

        Agenda agenda = new Agenda();
        agenda.setLocalDate(data.localDate());
        agenda.setLocalTime(data.localTime());
        agenda.setNutritionist(nutriotionistOpt.get());

        agendaRepository.save(agenda);

        return CreateAgendaRequestDto.fromtEntity(agenda.getNutritionist().getCrm(), agenda.getLocalDate(),
                agenda.getLocalTime());
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
                        a.isDisponibility())).collect(Collectors.toList());

        return agendaResponseDtos;
    }
}

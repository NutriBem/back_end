package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PersonCreateResponseDto;
import com.example.demo.dto.nutritionist.NutritionistResponseDto;
import com.example.demo.model.Nutritionist;
import com.example.demo.repository.NutritionistRepository;
import com.example.demo.validations.NutritionistValidation;
import com.example.demo.validations.PersonValidation;

@Service
public class NutritionistService {

    private NutritionistRepository nutritionistRepository;
    private NutritionistValidation nutritionistValidation;
    private PersonValidation personValidation;
    private PersonService personService;

    public NutritionistService(NutritionistRepository nutritionistRepository,
            NutritionistValidation nutritionistValidation, PersonValidation personValidation,
            PersonService personService) {
        this.nutritionistRepository = nutritionistRepository;
        this.nutritionistValidation = nutritionistValidation;
        this.personValidation = personValidation;
        this.personService = personService;
    }

    public PersonCreateResponseDto create(Nutritionist nutritionist) {
        personValidation.create(nutritionist);
        nutritionistValidation.create(nutritionist);

        return PersonCreateResponseDto.fromtEntity(personService.createPerson(nutritionist));
    }

    public NutritionistResponseDto getByCrm(String crm) {
        Optional<Nutritionist> nutritionistOptinal = nutritionistRepository.findByCrm(crm);

        if (nutritionistOptinal.isEmpty())
            throw new IllegalArgumentException("Nutricionista não encontrado.");

        NutritionistResponseDto response = new NutritionistResponseDto(
                nutritionistOptinal.get().getId(),
                nutritionistOptinal.get().getCrm(),
                nutritionistOptinal.get().getName(),
                nutritionistOptinal.get().getEmail(),
                nutritionistOptinal.get().getTelephone());

        return response;
    }

    public List<NutritionistResponseDto> getAll() {
        List<Nutritionist> nutritionists = nutritionistRepository.findAll();

        List<NutritionistResponseDto> nutritionistResponseDtos = nutritionists.stream()
                .map(n -> new NutritionistResponseDto(n.getId(), n.getCrm(), n.getName(), n.getEmail(),
                        n.getTelephone()))
                .collect(Collectors.toList());

        return nutritionistResponseDtos;
    }
}

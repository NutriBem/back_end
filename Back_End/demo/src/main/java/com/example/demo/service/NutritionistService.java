package com.example.demo.service;

import java.util.Optional;

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

    public NutritionistService(NutritionistRepository nutritionistRepository,
            NutritionistValidation nutritionistValidation, PersonValidation personValidation) {
        this.nutritionistRepository = nutritionistRepository;
        this.nutritionistValidation = nutritionistValidation;
        this.personValidation = personValidation;
    }

    public PersonCreateResponseDto create(Nutritionist nutritionist) {
        personValidation.create(nutritionist);
        nutritionistValidation.create(nutritionist);

        return PersonCreateResponseDto.fromtEntity(nutritionistRepository.save(nutritionist));
    }

    public NutritionistResponseDto getByCrm(String crm) {
        Optional<Nutritionist> nutritionistOptinal = nutritionistRepository.findByCrm(crm);

        if (nutritionistOptinal.isEmpty())
            throw new IllegalArgumentException("Nutricionista não encontrado.");

        NutritionistResponseDto response = new NutritionistResponseDto(
                nutritionistOptinal.get().getCrm(),
                nutritionistOptinal.get().getName(),
                nutritionistOptinal.get().getEmail(),
                nutritionistOptinal.get().getTelephone());

        return response;
    }
}

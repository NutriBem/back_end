package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PersonCreateResponseDto;
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
}

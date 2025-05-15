package com.example.demo.validations;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.model.Nutritionist;
import com.example.demo.repository.NutritionistRepository;

@Component
public class NutritionistValidation extends Validation {

    private NutritionistRepository nutritionistRepository;

    public NutritionistValidation(NutritionistRepository nutritionistRepository) {
        this.nutritionistRepository = nutritionistRepository;
    }

    public void create(Nutritionist nutritionist) {
        if (nutritionistRepository.existsByCrm(nutritionist.getCrm()))
            throw new IllegalArgumentException("Nutricionista já cadastrado.");
    }

    public Optional<Nutritionist> findByCrm(String crm) {
        return nutritionistRepository.findByCrm(crm);
    }
}

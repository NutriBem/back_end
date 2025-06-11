package com.example.demo.dto.nutritionist;

import java.util.UUID;

import com.example.demo.model.Nutritionist;

public record NutritionistResponseDto(UUID id, String crm, String name, String email, String telephone) {
    public static NutritionistResponseDto fromtEntity(Nutritionist nutritionist) {
        return new NutritionistResponseDto(
                nutritionist.getId(),
                nutritionist.getCrm(),
                nutritionist.getName(),
                nutritionist.getEmail(),
                nutritionist.getTelephone());
    }
}
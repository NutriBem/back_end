package com.example.demo.dto.nutritionist;

import com.example.demo.model.Nutritionist;

public record NutritionistResponseDto(String crm, String name, String email, String telephone) {
    public static NutritionistResponseDto fromtEntity(Nutritionist nutritionist) {
        return new NutritionistResponseDto(
                nutritionist.getCrm(),
                nutritionist.getName(),
                nutritionist.getEmail(),
                nutritionist.getTelephone());
    }
}
package com.example.demo.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PersonCreateResponseDto;
import com.example.demo.model.Nutritionist;
import com.example.demo.service.NutritionistService;

@RestController
@RequestMapping("/nutritionist")
public class NutritionistController {

    public NutritionistService nutritionistService;

    public NutritionistController(NutritionistService nutritionistService) {
        this.nutritionistService = nutritionistService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Nutritionist nutritionist) {
        try {
            PersonCreateResponseDto responseDto = nutritionistService.create(nutritionist);
            return ResponseEntity.status(201).body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }
}

package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PersonCreateResponseDto;
import com.example.demo.dto.nutritionist.NutritionistResponseDto;
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }

    @GetMapping("/crm/{crm}")
    public ResponseEntity<?> getByCrm(@PathVariable String crm) {
        try {
            NutritionistResponseDto nutriotionist = nutritionistService.getByCrm(crm);

            return ResponseEntity.ok().body(nutriotionist);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<NutritionistResponseDto> nutritionistResponseDtos = nutritionistService.getAll();
            return ResponseEntity.ok().body(nutritionistResponseDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());

        }
    }
}

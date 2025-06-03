package com.example.demo.validations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Array;
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

    public void validateCrm(String crm){
        final List<String> UFs = Arrays.asList(
    "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
        "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
        "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
        );

        if (crm == null || crm.trim().isEmpty())
            throw new IllegalArgumentException("CRM vazio");

        crm = crm.trim().toUpperCase();

        //formato básico: dígitos + / + letras
        if (!crm.matches("^\\d{1,15}/[A-Z]{2}$"))
            throw new IllegalArgumentException("Formato de crm invalido");
        
        //dividir em partes 
        String[] partes = crm.split("/");
        String uf = partes[1];

        if(!UFs.contains(uf))
            throw new IllegalArgumentException("Crm não valido!");
    }
}

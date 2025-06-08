package com.example.demo.scheduled;

import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.Nutritionist;
import com.example.demo.repository.NutritionistRepository;
import com.example.demo.singleton.BussinesHours;

@Component
public class ScheduledAgenda {
    
    private NutritionistRepository nutritionistRepository;

    public ScheduledAgenda(NutritionistRepository nutritionistRepository) {
        this.nutritionistRepository = nutritionistRepository;
    }

    @Scheduled(cron = "* * 23 * * *")
    public void createAgenda() {
        List<Nutritionist> nutritionists = nutritionistRepository.findAll();

        // horário de funcionamento da clínica
            List<LocalTime> openingHours = BussinesHours.hours();

        for (Nutritionist nutritionist  : nutritionists) {
            
        }
        
        
    }
}

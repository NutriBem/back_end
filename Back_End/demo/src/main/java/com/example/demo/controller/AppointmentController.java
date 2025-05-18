package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public void create(@RequestBody AppointmentCreateRequestDto appointment) {
        try {
            System.out.println("Controller");
           ResponseEntity.ok().body(appointmentService.create(appointment)); 

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

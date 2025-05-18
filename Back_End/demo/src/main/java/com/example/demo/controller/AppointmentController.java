package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.dto.appointment.AppointmentResponseDto;
import com.example.demo.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentCreateRequestDto appointment) {
        try {
            // criar uma response DTO para a consulta
            AppointmentResponseDto responseDto = appointmentService.create(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // body(createAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ERROR:" + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok().body(appointmentService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.agenda.CreateAgendaRequestDto;
import com.example.demo.service.AgendaService;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAgendaRequestDto data) {
        try {
            agendaService.create(data);
            return ResponseEntity.status(201).body("Agenda criada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }
}

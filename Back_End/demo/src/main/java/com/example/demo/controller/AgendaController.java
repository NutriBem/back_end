package com.example.demo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.agenda.AgendaResponseDto;
import com.example.demo.service.AgendaService;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    // @PostMapping
    // public ResponseEntity<?> create(@RequestBody CreateAgendaRequestDto data) {
    //     try {
    //         CreateAgendaRequestDto response = agendaService.create(data);
    //         return ResponseEntity.status(201).body(response);
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
    //     }
    // }

    // Pesquisar a agenda do nutrólogo
    @GetMapping("{crm}")
    public ResponseEntity<?> getAgendaByNutritionist(@PathVariable("crm") String crm) {
        try {
            List<AgendaResponseDto> responseDto = agendaService.getAgendaByCrmNutritionist(crm);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<AgendaResponseDto> agenadList = agendaService.getAll();

            return ResponseEntity.ok().body(agenadList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }
}

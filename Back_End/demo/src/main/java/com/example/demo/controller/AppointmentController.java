package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PdfRequestDto;
import com.example.demo.dto.appointment.AppointmentCreateRequestDto;
import com.example.demo.dto.appointment.AppointmentResponseDto;
import com.example.demo.model.Appointment;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentService appointmentService;

    private AppointmentRepository appointmentRepository;

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

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            AppointmentResponseDto response = appointmentService.getById(id);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            appointmentService.deleteById(id);
            return ResponseEntity.ok().body("Consulta excluída com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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

    @PostMapping("/{id}/pdf")
    public ResponseEntity<?> createPdf(@PathVariable Long id, @RequestBody PdfRequestDto requestDto){
        Appointment appointment = appointmentRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        
        appointment.generatedPdf(requestDto.getTitle(), requestDto.getParagraph());
        appointmentRepository.save(appointment);

        return ResponseEntity.ok("Pdf gerado e amarzaenado com sucesso ^~^");
    }
    
    @GetMapping("/{id}/pdf")
    public ResponseEntity<?> getPdf(@PathVariable Long id){
        Appointment appointment = appointmentRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (appointment.getPdf() == null || appointment.getPdf().getArquivo() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment",  "consulta_" + id + ".pdf");

        return ResponseEntity.ok()
            .headers(headers)
            .body(appointment.getPdf().getArquivo());
    }
}

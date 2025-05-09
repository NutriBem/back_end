package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonLoginDTO(
        @NotBlank(message = "Informe o e-mail.") 
        String email,

        @NotBlank(message = "Informe e senha.")
        String password
) {}

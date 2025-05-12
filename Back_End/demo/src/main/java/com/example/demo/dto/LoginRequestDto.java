package com.example.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto( String email, String password) {
}

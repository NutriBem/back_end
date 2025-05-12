package com.example.demo.dto;

import java.util.UUID;

import com.example.demo.model.Person;

public record LoginResponseDto(UUID id) {
    public static LoginResponseDto fromEntity(Person person) {
        return new LoginResponseDto(person.getId());
    }
}

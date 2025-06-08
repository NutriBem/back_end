package com.example.demo.dto;

import java.util.UUID;

import com.example.demo.model.Person;

public record PersonResponseDto(UUID id) {
    public static PersonResponseDto fromEntity(Person person) {
        return new PersonResponseDto(
            person.getId()
        );
    }
}

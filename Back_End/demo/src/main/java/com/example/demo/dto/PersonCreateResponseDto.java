package com.example.demo.dto;

import java.util.UUID;

import com.example.demo.model.Person;

public record PersonCreateResponseDto(UUID id) {
    public static PersonCreateResponseDto fromtEntity(Person person) {
        return new PersonCreateResponseDto(person.getId());
    }
}

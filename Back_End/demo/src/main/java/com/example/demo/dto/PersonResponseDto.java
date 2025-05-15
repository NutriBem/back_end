package com.example.demo.dto;

import com.example.demo.model.Person;

public record PersonResponseDto(String name, String email, String telephone) {
    public static PersonResponseDto fromEntity(Person person) {
        return new PersonResponseDto(
            person.getName(),
            person.getEmail(),
            person.getTelephone()
        );
    }
}

package com.example.demo.dto;

import java.util.UUID;

public record PersonResponseIdDto(UUID id, byte type, String name) {
    public static PersonResponseIdDto fromEntity(UUID id, byte type, String name) {
        return new PersonResponseIdDto(id, type, name);
    }
}

package com.example.demo.dto;

import java.util.UUID;

public record PersonResponseIdDto(UUID id, byte type) {
    public static PersonResponseIdDto fromEntity(UUID id, byte type) {
        return new PersonResponseIdDto(id, type);
    }
}

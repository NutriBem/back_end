package com.example.demo.dto;

import java.util.UUID;

public record PersonResponseIdDto(UUID id, byte type, String name, String email, String telefone) {
    public static PersonResponseIdDto fromEntity(UUID id, byte type, String name, String email, String telefone) {
        return new PersonResponseIdDto(id, type, name, email, telefone);
    }
}

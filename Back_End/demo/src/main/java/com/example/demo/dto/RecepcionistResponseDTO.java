package com.example.demo.dto;

import com.example.demo.model.Recepcionist;
import java.util.UUID;

public record RecepcionistResponseDTO(
    UUID id,
    String name,
    String email,
    String telephone
) {
    public static RecepcionistResponseDTO fromEntity(Recepcionist recepcionist) {
        return new RecepcionistResponseDTO(
            recepcionist.getId(),
            recepcionist.getName(), //Data🤬🤬🤬🤬
            recepcionist.getEmail(),
            recepcionist.getTelephone()
        );
    }
}
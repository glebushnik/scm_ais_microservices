package com.example.transport_service.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TransportTypeDTO(
        UUID id,
        @NotBlank
        @Size(min = 4, max = 20)
        String name
) {
}

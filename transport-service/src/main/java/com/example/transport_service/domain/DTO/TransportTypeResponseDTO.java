package com.example.transport_service.domain.DTO;

import java.util.UUID;

public record TransportTypeResponseDTO(
        UUID id,
        String name
) {
}

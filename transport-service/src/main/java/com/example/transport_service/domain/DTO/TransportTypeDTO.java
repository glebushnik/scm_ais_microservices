package com.example.transport_service.domain.DTO;

import java.util.UUID;

public record TransportTypeDTO(
        UUID id,
        String name
) {
}

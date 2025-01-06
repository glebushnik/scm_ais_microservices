package com.example.transport_service.domain.DTO;

import java.util.UUID;

public record TransportResponseDTO(
        UUID id,
        String volume,
        String regNumber,
        String transportType
) {
}

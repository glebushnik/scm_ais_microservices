package com.example.transport_service.domain.DTO;


import java.util.UUID;

public record TransportDTO(
        UUID id,
        String volume,
        String regNumber,
        String transportType
){
}

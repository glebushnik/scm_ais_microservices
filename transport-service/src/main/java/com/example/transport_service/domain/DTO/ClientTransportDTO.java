package com.example.transport_service.domain.DTO;


import java.util.UUID;

public record ClientTransportDTO(
        UUID driverId,
        UUID transportType,
        String regNumber,
        String volume
){}
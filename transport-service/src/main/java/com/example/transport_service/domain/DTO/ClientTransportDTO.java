package com.example.transport_service.domain.DTO;

import com.example.transport_service.domain.entity.TransportType;
import com.example.transport_service.domain.enums.CarType;

import java.util.UUID;

public record ClientTransportDTO(
        UUID driverId,
        UUID transportType,
        String regNumber,
        String volume
){}
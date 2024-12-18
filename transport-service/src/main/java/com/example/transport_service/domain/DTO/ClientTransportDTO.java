package com.example.transport_service.domain.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

public record ClientTransportDTO(
        @NotBlank
        UUID driverId,
        UUID transportType,
        @NotBlank
        @Size(min = 6, max = 8)
        String regNumber,
        @NotBlank
        @Size(min = 6, max = 8)
        String volume
){}
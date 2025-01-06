package com.glebushnik.warehouse_service.domain.DTO;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record WarehouseItemAssignmentDTO(
        @NotBlank
        UUID warehouseId,
        @NotBlank
        UUID itemId
) {
}

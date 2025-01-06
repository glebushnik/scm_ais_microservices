package com.glebushnik.warehouse_service.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record WarehouseClientDTO(
        UUID companyId,

        String warehouseName,
        @NotBlank
        @Size(min = 10)
        String address
) {
}

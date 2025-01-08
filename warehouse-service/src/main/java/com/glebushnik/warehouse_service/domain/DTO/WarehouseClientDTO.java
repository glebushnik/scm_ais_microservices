package com.glebushnik.warehouse_service.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record WarehouseClientDTO(
        UUID companyId,

        @NotBlank
        @Size(min = 5)
        String warehouseName,
        @NotBlank
        String coords,
        @NotBlank
        @Size(min = 10)
        String address
) {
}

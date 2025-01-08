package com.glebushnik.warehouse_service.domain.DTO;

import java.util.UUID;

public record ItemResponseDTO(
        UUID id,
        String name,
        String volume,
        Integer quantity,
        Boolean isFragile,
        UUID warehouseId
) {
}

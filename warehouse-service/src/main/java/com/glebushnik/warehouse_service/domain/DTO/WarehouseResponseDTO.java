package com.glebushnik.warehouse_service.domain.DTO;

import java.util.UUID;
import java.util.List;

public record WarehouseResponseDTO(
        UUID id,
        String warehouseName,
        UUID companyId,
        String coords,
        String address,
        List<UUID> itemIds,
        List<UUID> transportIds,
        List<UUID> userIds
) {
}

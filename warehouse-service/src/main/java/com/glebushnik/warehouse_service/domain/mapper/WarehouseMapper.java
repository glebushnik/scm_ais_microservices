package com.glebushnik.warehouse_service.domain.mapper;

import com.glebushnik.warehouse_service.domain.DTO.WarehouseClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.WarehouseResponseDTO;
import com.glebushnik.warehouse_service.domain.entity.Warehouse;
import org.springframework.stereotype.Service;
import com.glebushnik.warehouse_service.domain.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarehouseMapper {
    public Warehouse mapWarehouse(Warehouse oldWarehouse, WarehouseClientDTO newWarehouse) {
        oldWarehouse.setWarehouseName(newWarehouse.warehouseName() != null ? newWarehouse.warehouseName() : oldWarehouse.getWarehouseName());
        oldWarehouse.setCompanyId(newWarehouse.companyId()!=null ? newWarehouse.companyId() : oldWarehouse.getCompanyId());
        oldWarehouse.setAddress(newWarehouse.address() != null ? newWarehouse.address() : oldWarehouse.getAddress());
        return oldWarehouse;
    }

    public WarehouseResponseDTO entityToDto(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        }

        List<Item> items = warehouse.getItems();
        List<UUID> itemIds = (items == null || items.isEmpty())
                ? null
                : items.stream()
                .filter(Objects::nonNull)
                .map(Item::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new WarehouseResponseDTO(
                warehouse.getId(),
                warehouse.getWarehouseName(),
                warehouse.getCompanyId(),
                warehouse.getCoords(),
                warehouse.getAddress(),
                itemIds,
                warehouse.getTransportIds(),
                warehouse.getUserIds()
        );
    }
}

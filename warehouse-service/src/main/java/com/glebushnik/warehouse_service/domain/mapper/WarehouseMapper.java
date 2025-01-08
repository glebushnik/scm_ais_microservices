package com.glebushnik.warehouse_service.domain.mapper;

import com.glebushnik.warehouse_service.domain.DTO.WarehouseClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.WarehouseResponseDTO;
import com.glebushnik.warehouse_service.domain.entity.Warehouse;
import org.springframework.stereotype.Service;
import com.glebushnik.warehouse_service.domain.entity.Item;

import java.util.List;
import java.util.UUID;

@Service
public class WarehouseMapper {
    public Warehouse mapWarehouse(Warehouse oldWarehouse, WarehouseClientDTO newWarehouse) {
        oldWarehouse.setWarehouseName(newWarehouse.warehouseName() != null ? newWarehouse.warehouseName() : oldWarehouse.getWarehouseName());
        oldWarehouse.setCompanyId(newWarehouse.companyId()!=null ? newWarehouse.companyId() : oldWarehouse.getCompanyId());
        oldWarehouse.setAddress(newWarehouse.address() != null ? newWarehouse.address() : oldWarehouse.getAddress());
        return oldWarehouse;
    }

    public WarehouseResponseDTO entityToDto(Warehouse warehouse) {

        List<UUID> itemIds = warehouse.getItems().stream()
                .map(Item::getId)
                .toList();

        return new WarehouseResponseDTO(
                warehouse.getId(),
                warehouse.getWarehouseName(),
                warehouse.getCompanyId(),
                warehouse.getCoords(),
                warehouse.getAddress(),
                warehouse.getItems().stream()
                        .map(Item::getId)
                        .toList(),
                warehouse.getTransportIds(),
                warehouse.getUserIds()
        );
    }
}

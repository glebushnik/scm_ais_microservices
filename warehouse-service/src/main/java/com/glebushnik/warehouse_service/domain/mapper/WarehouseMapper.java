package com.glebushnik.warehouse_service.domain.mapper;

import com.glebushnik.warehouse_service.domain.DTO.WarehouseClientDTO;
import com.glebushnik.warehouse_service.domain.entity.Warehouse;
import org.springframework.stereotype.Service;

@Service
public class WarehouseMapper {
    public Warehouse mapWarehouse(Warehouse oldWarehouse, WarehouseClientDTO newWarehouse) {
        oldWarehouse.setWarehouseName(newWarehouse.warehouseName() != null ? newWarehouse.warehouseName() : oldWarehouse.getWarehouseName());
        oldWarehouse.setCompanyId(newWarehouse.companyId()!=null ? newWarehouse.companyId() : oldWarehouse.getCompanyId());
        oldWarehouse.setAddress(newWarehouse.address() != null ? newWarehouse.address() : oldWarehouse.getAddress());
        return oldWarehouse;
    }
}

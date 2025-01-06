package com.glebushnik.warehouse_service.service;

import com.glebushnik.warehouse_service.domain.DTO.ItemClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.WarehouseClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.WarehouseItemAssignmentDTO;
import com.glebushnik.warehouse_service.domain.entity.Item;
import com.glebushnik.warehouse_service.domain.entity.Warehouse;
import com.glebushnik.warehouse_service.exception.ItemNotFoundByIdException;
import com.glebushnik.warehouse_service.exception.WarehouseNotFoundByIdException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WarehouseService {
    public Warehouse createWarehouse(WarehouseClientDTO warehouseClientDTO);
    public void deleteWarehouseById(UUID warehouseId) throws WarehouseNotFoundByIdException;
    public Warehouse updateWarehouseById(WarehouseClientDTO updatedWarehouse, UUID warehouseId) throws WarehouseNotFoundByIdException;
    public Warehouse getWarehouseById(UUID warehouseId) throws WarehouseNotFoundByIdException;
    public List<ItemClientDTO> getItemsByWarehouse(UUID warehouseId) throws WarehouseNotFoundByIdException;
    public List<Warehouse> getAllWarehouses();
    public String addItemById(WarehouseItemAssignmentDTO warehouseItemAssignmentDTO) throws ItemNotFoundByIdException, WarehouseNotFoundByIdException;
    public void removeItemById(WarehouseItemAssignmentDTO warehouseItemAssignmentDTO) throws ItemNotFoundByIdException, WarehouseNotFoundByIdException;
}

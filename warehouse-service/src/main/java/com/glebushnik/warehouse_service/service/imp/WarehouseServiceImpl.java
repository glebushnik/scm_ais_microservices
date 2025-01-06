package com.glebushnik.warehouse_service.service.imp;


import com.glebushnik.warehouse_service.domain.DTO.ItemClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.WarehouseClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.WarehouseItemAssignmentDTO;
import com.glebushnik.warehouse_service.domain.entity.Item;
import com.glebushnik.warehouse_service.domain.entity.Warehouse;
import com.glebushnik.warehouse_service.domain.mapper.ItemMapper;
import com.glebushnik.warehouse_service.domain.mapper.WarehouseMapper;
import com.glebushnik.warehouse_service.exception.ItemNotFoundByIdException;
import com.glebushnik.warehouse_service.exception.WarehouseNotFoundByIdException;
import com.glebushnik.warehouse_service.repo.ItemRepo;
import com.glebushnik.warehouse_service.repo.WarehouseRepo;
import com.glebushnik.warehouse_service.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepo warehouseRepo;
    private final ItemRepo itemRepo;
    private final WarehouseMapper warehouseMapper;
    private final ItemMapper itemMapper;
    @Override
    public Warehouse createWarehouse(WarehouseClientDTO warehouseClientDTO) {
        return warehouseRepo.save(
                Warehouse.builder()
                        .address(warehouseClientDTO.address())
                        .warehouseName(warehouseClientDTO.warehouseName())

                        .build()
        );
    }

    @Override
    public void deleteWarehouseById(UUID warehouseId) throws WarehouseNotFoundByIdException {
        if (warehouseRepo.findById(warehouseId).isEmpty()) {
            throw new WarehouseNotFoundByIdException("Склад с id: " + warehouseId + " не найден.");
        }

        warehouseRepo.deleteById(warehouseId);
    }

    @Override
    public Warehouse updateWarehouseById(WarehouseClientDTO newWarehouse, UUID warehouseId) throws WarehouseNotFoundByIdException {
        Warehouse oldWarehouse = warehouseRepo.findById(warehouseId).orElseThrow(
                () -> new WarehouseNotFoundByIdException("Склад с id: " + warehouseId + " не найден.")
        );
        return warehouseRepo.save(warehouseMapper.mapWarehouse(oldWarehouse, newWarehouse));
    }

    @Override
    public Warehouse getWarehouseById(UUID warehouseId) throws WarehouseNotFoundByIdException {
        return warehouseRepo.findById(warehouseId).orElseThrow(
                () -> new WarehouseNotFoundByIdException("Склад с id: " + warehouseId + " не найден."));
    }

    @Override
    public List<ItemClientDTO> getItemsByWarehouse(UUID warehouseId) throws WarehouseNotFoundByIdException {
        return warehouseRepo.findById(warehouseId).orElseThrow(
                () -> new WarehouseNotFoundByIdException("Склад с id: " + warehouseId + " не найден.")
        ).getItems().stream().map(itemMapper::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepo.findAll();
    }

    @Override
    public String addItemById(WarehouseItemAssignmentDTO warehouseItemAssignmentDTO) throws ItemNotFoundByIdException, WarehouseNotFoundByIdException {
        Warehouse warehouse = warehouseRepo.findById(warehouseItemAssignmentDTO.warehouseId()).orElseThrow(
                () -> new WarehouseNotFoundByIdException("Склад с id: " + warehouseItemAssignmentDTO.warehouseId() + " не найден.")
        );

        Item item = itemRepo.findById(warehouseItemAssignmentDTO.itemId()).orElseThrow(
                () -> new ItemNotFoundByIdException("Товар с id: " + warehouseItemAssignmentDTO.itemId() + " не найден.")
        );

        var warehouseItems = warehouse.getItems();
        warehouseItems.add(item);
        warehouse.setItems(warehouseItems);
        warehouseRepo.save(warehouse);
        return "Товар с id: " + warehouseItemAssignmentDTO.itemId() + " был добавлен на склад с id: " + warehouseItemAssignmentDTO.warehouseId() +".";
    }

    @Override
    public void removeItemById(WarehouseItemAssignmentDTO warehouseItemAssignmentDTO) throws ItemNotFoundByIdException, WarehouseNotFoundByIdException {
        Warehouse warehouse = warehouseRepo.findById(warehouseItemAssignmentDTO.warehouseId()).orElseThrow(
                () -> new WarehouseNotFoundByIdException("Склад с id: " + warehouseItemAssignmentDTO.warehouseId() + " не найден.")
        );

        Item item = itemRepo.findById(warehouseItemAssignmentDTO.itemId()).orElseThrow(
                () -> new ItemNotFoundByIdException("Товар с id: " + warehouseItemAssignmentDTO.itemId() + " не найден.")
        );
        var warehouseItems = warehouse.getItems();
        warehouseItems.remove(item);
        warehouse.setItems(warehouseItems);
        warehouseRepo.save(warehouse);
    }
}

package com.glebushnik.warehouse_service.service.imp;

import com.glebushnik.warehouse_service.domain.DTO.ItemClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.ItemResponseDTO;
import com.glebushnik.warehouse_service.domain.DTO.UpdateItemDTO;
import com.glebushnik.warehouse_service.domain.entity.Item;
import com.glebushnik.warehouse_service.domain.entity.Warehouse;
import com.glebushnik.warehouse_service.domain.mapper.ItemMapper;
import com.glebushnik.warehouse_service.exception.ItemNotFoundByIdException;
import com.glebushnik.warehouse_service.exception.WarehouseNotFoundByIdException;
import com.glebushnik.warehouse_service.repo.ItemRepo;
import com.glebushnik.warehouse_service.repo.WarehouseRepo;
import com.glebushnik.warehouse_service.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepo itemRepo;
    private final WarehouseRepo warehouseRepo;
    private final ItemMapper itemMapper;
    @Override
    public ItemResponseDTO createItem(ItemClientDTO itemClientDTO) throws WarehouseNotFoundByIdException {
        Warehouse warehouse = warehouseRepo.findById(itemClientDTO.warehouseId())
                .orElseThrow(()->new WarehouseNotFoundByIdException("Склад с id: " + itemClientDTO.warehouseId() + " не найден."));


        Item item = itemRepo.save(
                Item.builder()
                        .warehouse(warehouse)
                        .volume(itemClientDTO.volume())
                        .name(itemClientDTO.name())
                        .quantity(itemClientDTO.quantity())
                        .isFragile(itemClientDTO.isFragile())
                        .build()
        );
        return new ItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getVolume(),
                item.getQuantity(),
                item.getIsFragile(),
                item.getWarehouse().getId()
        );
    }

    @Override
    public void deleteItemById(UUID itemId) throws ItemNotFoundByIdException {
        if (itemRepo.findById(itemId).isEmpty()) {
            throw new ItemNotFoundByIdException("Товар с id: " + itemId + " не найден.");
        }

        itemRepo.deleteById(itemId);
    }

    @Override
    public Item updateItemById(UUID oldItemId, UpdateItemDTO newItem) throws ItemNotFoundByIdException {
        Item item = itemRepo.findById(oldItemId).orElseThrow(
                () -> new ItemNotFoundByIdException("Товар с id: " + oldItemId + " не найден.")
        );

        return itemRepo.save(itemMapper.mapItem(item, newItem));
    }
}

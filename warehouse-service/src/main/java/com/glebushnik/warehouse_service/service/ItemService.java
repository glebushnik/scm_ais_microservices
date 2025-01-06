package com.glebushnik.warehouse_service.service;

import com.glebushnik.warehouse_service.domain.DTO.ItemClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.UpdateItemDTO;
import com.glebushnik.warehouse_service.domain.entity.Item;
import com.glebushnik.warehouse_service.exception.ItemNotFoundByIdException;
import com.glebushnik.warehouse_service.exception.WarehouseNotFoundByIdException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ItemService {
    public Item createItem(ItemClientDTO itemClientDTO) throws WarehouseNotFoundByIdException;
    public void deleteItemById(UUID itemId) throws ItemNotFoundByIdException;
    public Item updateItemById(UUID oldItemId, UpdateItemDTO newItem) throws ItemNotFoundByIdException;
}

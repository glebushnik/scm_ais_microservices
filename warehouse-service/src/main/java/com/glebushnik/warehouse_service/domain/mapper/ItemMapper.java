package com.glebushnik.warehouse_service.domain.mapper;

import com.glebushnik.warehouse_service.domain.DTO.ItemClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.UpdateItemDTO;
import com.glebushnik.warehouse_service.domain.entity.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemMapper {
    public ItemClientDTO entityToDTO(Item item) {
        return new ItemClientDTO(
                item.getName(),
                item.getVolume(),
                item.getQuantity(),
                item.getIsFragile(),
                item.getWarehouse().getId()
        );
    }

    public Item mapItem(Item oldItem, UpdateItemDTO newItem) {
        return Item.builder()
                .name(newItem.name() != null ? newItem.name() : oldItem.getName())
                .volume(newItem.volume() != null ? newItem.volume() : oldItem.getVolume())
                .quantity(newItem.quantity() != null ? newItem.quantity() : oldItem.getQuantity())
                .isFragile(newItem.isFragile() != null ? newItem.isFragile() : oldItem.getIsFragile())
                .build();
    }
}

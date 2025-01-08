package com.glebushnik.warehouse_service.controller;

import com.glebushnik.warehouse_service.domain.DTO.ItemClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.ItemResponseDTO;
import com.glebushnik.warehouse_service.domain.DTO.UpdateItemDTO;
import com.glebushnik.warehouse_service.domain.entity.Item;
import com.glebushnik.warehouse_service.exception.ItemNotFoundByIdException;
import com.glebushnik.warehouse_service.exception.WarehouseNotFoundByIdException;
import com.glebushnik.warehouse_service.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouse/items")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/create")
    @Operation(
            summary = "Создать новый товар.",
            description = "Создает новый товар на основе переданных данных. Товар привязывается к указанному складу.",
            tags = { "item", "create", "post" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успешно создан.", content = { @Content(schema = @Schema(implementation = ItemResponseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Некорректные данные или склад не найден.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> createItem(@Valid @RequestBody ItemClientDTO itemClientDTO) {
        try {
            return ResponseEntity.ok().body(itemService.createItem(itemClientDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{itemId}")
    @Operation(
            summary = "Удалить товар по ID.",
            description = "Удаляет товар с указанным ID. Если товар не найден, возвращает ошибку.",
            tags = { "item", "delete" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успешно удален.", content = { @Content(schema = @Schema(example = "Товар с id: {itemId} успешно удален."), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Товар не найден или произошла ошибка при удалении.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> deleteItem(@PathVariable UUID itemId) {
        try {
            itemService.deleteItemById(itemId);
            return ResponseEntity.ok().body("Товар с id: " + itemId + " успешно удален.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{itemId}")
    @Operation(
            summary = "Обновить товар по ID.",
            description = "Обновляет данные товара с указанным ID на основе переданных данных. Если товар не найден, возвращает ошибку.",
            tags = { "item", "update", "put" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успешно обновлен.", content = { @Content(schema = @Schema(implementation = Item.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Некорректные данные или товар не найден.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> updateItemById(@PathVariable UUID itemId, @Valid @RequestBody UpdateItemDTO newItem) {
        try {
            return ResponseEntity.ok().body(itemService.updateItemById(itemId, newItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

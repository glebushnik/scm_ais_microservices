package com.glebushnik.warehouse_service.controller;

import com.glebushnik.warehouse_service.domain.DTO.ItemClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.WarehouseClientDTO;
import com.glebushnik.warehouse_service.domain.DTO.WarehouseItemAssignmentDTO;
import com.glebushnik.warehouse_service.domain.entity.Warehouse;
import com.glebushnik.warehouse_service.service.WarehouseService;
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
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping("/create")
    @Operation(
            summary = "Создать новый склад.",
            description = "Создает новый склад на основе переданных данных.",
            tags = { "warehouse", "create", "post" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Склад успешно создан.", content = { @Content(schema = @Schema(implementation = Warehouse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Некорректные данные.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> createWarehouse(@Valid @RequestBody WarehouseClientDTO warehouseClientDTO) {
        try {
            return ResponseEntity.ok().body(warehouseService.createWarehouse(warehouseClientDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{warehouseId}")
    @Operation(
            summary = "Удалить склад по ID.",
            description = "Удаляет склад с указанным ID. Если склад не найден, возвращает ошибку.",
            tags = { "warehouse", "delete" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Склад успешно удален.", content = { @Content(schema = @Schema(example = "Склад с id: {warehouseId} успешно удален."), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Склад не найден или произошла ошибка при удалении.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> deleteWarehouseById(@PathVariable UUID warehouseId) {
        try {
            warehouseService.deleteWarehouseById(warehouseId);
            return ResponseEntity.ok().body("Склад с id: " + warehouseId + " успешно удален.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{warehouseId}")
    @Operation(
            summary = "Обновить склад по ID.",
            description = "Обновляет данные склада с указанным ID на основе переданных данных. Если склад не найден, возвращает ошибку.",
            tags = { "warehouse", "update", "put" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Склад успешно обновлен.", content = { @Content(schema = @Schema(implementation = Warehouse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Некорректные данные или склад не найден.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> updateWarehouseById(@PathVariable UUID warehouseId, @Valid @RequestBody WarehouseClientDTO newWarehouse) {
        try {
            return ResponseEntity.ok().body(warehouseService.updateWarehouseById(newWarehouse, warehouseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{warehouseId}")
    @Operation(
            summary = "Получить склад по ID.",
            description = "Возвращает данные склада с указанным ID. Если склад не найден, возвращает ошибку.",
            tags = { "warehouse", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Склад успешно найден.", content = { @Content(schema = @Schema(implementation = Warehouse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Склад не найден.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> getWarehouseById(@PathVariable UUID warehouseId) {
        try {
            return ResponseEntity.ok().body(warehouseService.getWarehouseById(warehouseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{warehouseId}/items")
    @Operation(
            summary = "Получить список товаров на складе.",
            description = "Возвращает список всех товаров, находящихся на складе с указанным ID. Если склад не найден, возвращает ошибку.",
            tags = { "warehouse", "items", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список товаров успешно получен.", content = { @Content(schema = @Schema(implementation = ItemClientDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Склад не найден.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> getItemsByWarehouse(@PathVariable UUID warehouseId) {
        try {
            return ResponseEntity.ok().body(warehouseService.getItemsByWarehouse(warehouseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получить список всех складов.",
            description = "Возвращает список всех существующих складов.",
            tags = { "warehouse", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список складов успешно получен.", content = { @Content(schema = @Schema(implementation = Warehouse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> getAllWarehouses() {
        try {
            return ResponseEntity.ok().body(warehouseService.getAllWarehouses());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add-item")
    @Operation(
            summary = "Добавить товар на склад.",
            description = "Добавляет товар с указанным ID на склад с указанным ID. Если товар или склад не найдены, возвращает ошибку.",
            tags = { "warehouse", "items", "post" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успешно добавлен на склад.", content = { @Content(schema = @Schema(example = "Товар с id: {itemId} был добавлен на склад с id: {warehouseId}."), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Товар или склад не найдены.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> addItemById(@Valid @RequestBody WarehouseItemAssignmentDTO warehouseItemAssignmentDTO) {
        try {
            return ResponseEntity.ok().body(warehouseService.addItemById(warehouseItemAssignmentDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove-item")
    @Operation(
            summary = "Удалить товар со склада.",
            description = "Удаляет товар с указанным ID со склада с указанным ID. Если товар или склад не найдены, возвращает ошибку.",
            tags = { "warehouse", "items", "delete" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успешно удален со склада.", content = { @Content(schema = @Schema(example = "Товар с id: {itemId} был удален со склада с id: {warehouseId}."), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Товар или склад не найдены.", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> removeItemById(@Valid @RequestBody WarehouseItemAssignmentDTO warehouseItemAssignmentDTO) {
        try {
            warehouseService.removeItemById(warehouseItemAssignmentDTO);
            return ResponseEntity.ok().body("Товар с id: " + warehouseItemAssignmentDTO.itemId() + " был удален со склада с id: " + warehouseItemAssignmentDTO.warehouseId() + ".");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
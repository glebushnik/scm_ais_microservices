package com.example.transport_service.controller;

import com.example.transport_service.domain.DTO.TransportTypeDTO;
import com.example.transport_service.domain.entity.TransportType;
import com.example.transport_service.service.TransportTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transport/types")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TransportTypeController {
    private final TransportTypeService transportTypeService;

    @PostMapping("/create")
    @Operation(
            summary = "Создать новый тип транспорта.",
            description = "Возвращает созданный тип транспорта.",
            tags = { "transport-types", "create" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TransportType.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = @Content)
    })
    public ResponseEntity<?> createTransportType(@RequestBody TransportTypeDTO transportType) {
        try {
            return ResponseEntity.ok().body(
                    transportTypeService.createTransportType(transportType)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получить список всех типов транспорта.",
            description = "Возвращает список всех доступных типов транспорта.",
            tags = { "transport-types", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TransportType.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = @Content)
    })
    public ResponseEntity<?> getAllTransportTypes() {
        try {
            return ResponseEntity.ok().body(transportTypeService.getAllTransportTypes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{transportTypeId}")
    @Operation(
            summary = "Удалить тип транспорта по его идентификатору.",
            description = "Удаляет тип транспорта и возвращает сообщение об успешном удалении.",
            tags = { "transport-types", "delete" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Тип транспорта не найден.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.", content = @Content)
    })
    public ResponseEntity<?> deleteTransportTypeById(@PathVariable UUID transportTypeId) {
        try {
            transportTypeService.deleteTransportType(transportTypeId);
            return ResponseEntity.ok().body(String.format("Тип транспорта с id : %s успешно удален.", transportTypeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
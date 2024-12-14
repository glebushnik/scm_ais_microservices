package com.example.transport_service.controller;

import com.example.transport_service.domain.DTO.ClientTransportDTO;
import com.example.transport_service.domain.DTO.TransportDTO;
import com.example.transport_service.service.TransportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transport")
@SecurityRequirement(name = "Bearer Authentication")
public class TransportController {

    private final TransportService transportService;

    @PostMapping("/create")
    @Operation(
            summary = "Создать транспортное средство.",
            description = "Создаёт транспортное средство на основе данных из ClientTransportDTO и возвращает ID созданного транспорта.",
            tags = { "admin", "transport", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    ResponseEntity<?> createCar(@RequestBody ClientTransportDTO carDTO) {
        try {
            var res = transportService.createTransport(carDTO);
            return ResponseEntity.ok().body(String.format("Транспорт с id : %s создан", res.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получить список всех транспортных средств.",
            description = "Возвращает список всех существующих транспортных средств.",
            tags = { "admin", "transport", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = List.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    ResponseEntity<?> getAllCars() {
        try {
            return ResponseEntity.ok().body(transportService.getAllTransport());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{transportId}")
    @Operation(
            summary = "Получить транспортное средство по ID.",
            description = "Возвращает транспортное средство, соответствующее указанному ID.",
            tags = { "admin", "transport", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TransportDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    ResponseEntity<?> getTransportById(@PathVariable UUID transportId) {
        try {
            return ResponseEntity.ok().body(transportService.getTransportById(transportId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{transportId}")
    @Operation(
            summary = "Удалить транспортное средство по ID.",
            description = "Удаляет транспортное средство, соответствующее указанному ID.",
            tags = { "admin", "transport", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    ResponseEntity<?> deleteTransportById(@PathVariable UUID transportId) {
        try {
            transportService.deleteTransportById(transportId);
            return ResponseEntity.ok().body(String.format("Транспорт с id : %s успешно удален", transportId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all/{userId}")
    @Operation(
            summary = "Получить все транспортные средства пользователя по его ID.",
            description = "Возвращает список всех транспортных средств, связанных с указанным пользователем.",
            tags = { "admin", "transport", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = List.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    ResponseEntity<?> getAllTransportByUserId(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok().body(transportService.getTransportByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

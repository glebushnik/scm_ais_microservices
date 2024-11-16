package com.example.transport_service.controller;

import com.example.transport_service.domain.entity.TransportType;
import com.example.transport_service.service.TransportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transport-types")
@RequiredArgsConstructor
public class TransportTypeController {
    private final TransportTypeService transportTypeService;

    @PostMapping("/create")
    ResponseEntity<?> createTransportType(@RequestBody TransportType transportType) {
        try {
            return ResponseEntity.ok().body(
              transportTypeService.createTransportType(transportType)
            );
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllTransportTypes() {
        try {
            return ResponseEntity.ok().body(transportTypeService.getAllTransportTypes());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{transportTypeId}")
    ResponseEntity<?> deleteTransportTypeById(@PathVariable UUID transportTypeId) {
        try {
            transportTypeService.deleteTransportType(transportTypeId);
            return ResponseEntity.ok().body(String.format("Тип транспорта с id : %s успешно удален.", transportTypeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

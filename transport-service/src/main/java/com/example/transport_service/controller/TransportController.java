package com.example.transport_service.controller;

import com.example.transport_service.domain.DTO.ClientTransportDTO;
import com.example.transport_service.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transport")
public class TransportController {
    private final TransportService transportService;

    @PostMapping("/create")
    ResponseEntity<?> createCar(@RequestBody ClientTransportDTO carDTO) {
        try {
            var res = transportService.createTransport(carDTO);
            return ResponseEntity.ok().body(String.format("Транспорт с id : %s создан", res.getId()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllCars() {
        try {
            return ResponseEntity.ok().body(transportService.getAllTransport());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{transportId}")
    ResponseEntity<?> getTransportById(@PathVariable UUID transportId) {
        try {
            return ResponseEntity.ok().body(transportService.getTransportById(transportId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{transportId}/change-driver/{driverId}")
    ResponseEntity<?> updateTransportDriver(@PathVariable UUID transportId, @PathVariable UUID driverId) {
        try {
            return ResponseEntity.ok().body(transportService.updateTransportDriverById(transportId, driverId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{transportId}")
    ResponseEntity<?> deleteTransportById(@PathVariable UUID transportId) {
        try {
            transportService.deleteTransportById(transportId);
            return ResponseEntity.ok().body(String.format("Транспорт с id : %s успшено удален", transportId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

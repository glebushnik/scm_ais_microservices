package com.example.transport_service.controller;

import com.example.transport_service.domain.DTO.ClientTransportDTO;
import com.example.transport_service.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

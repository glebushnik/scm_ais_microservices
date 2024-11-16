package com.example.transport_service.controller;

import com.example.transport_service.domain.entity.TransportType;
import com.example.transport_service.service.TransportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.example.transport_service.service;

import com.example.transport_service.domain.DTO.TransportDTO;
import com.example.transport_service.domain.DTO.ClientTransportDTO;
import com.example.transport_service.domain.entity.Transport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TransportService {
    Transport createTransport(ClientTransportDTO transportDTO);
    TransportDTO getTransportById(UUID id);
    List<TransportDTO> getAllTransport();
    TransportDTO updateTransportDriverById(UUID id, UUID driverId);
    void deleteTransportById(UUID id);
}

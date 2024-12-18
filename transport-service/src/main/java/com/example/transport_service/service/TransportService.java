package com.example.transport_service.service;

import com.example.transport_service.domain.DTO.TransportDTO;
import com.example.transport_service.domain.DTO.ClientTransportDTO;
import com.example.transport_service.domain.entity.Transport;
import com.example.transport_service.exception.TransportNotFoundByIdException;
import com.example.transport_service.exception.UserNotFoundByIdException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TransportService {
    Transport createTransport(ClientTransportDTO transportDTO) throws TransportNotFoundByIdException;
    TransportDTO getTransportById(UUID id) throws TransportNotFoundByIdException;
    List<TransportDTO> getAllTransport();
    void deleteTransportById(UUID id) throws TransportNotFoundByIdException;
    void assignUserToTransport(List<UUID> transportId, UUID userId);

    List<TransportDTO> getTransportByUserId(UUID userId) throws UserNotFoundByIdException;
}

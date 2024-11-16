package com.example.transport_service.service;

import com.example.transport_service.domain.entity.TransportType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TransportTypeService {
    public TransportType createTransportType(TransportType transportType);
    public void deleteTransportType(UUID transportTypeId);
    public List<TransportType> getAllTransportTypes();
}

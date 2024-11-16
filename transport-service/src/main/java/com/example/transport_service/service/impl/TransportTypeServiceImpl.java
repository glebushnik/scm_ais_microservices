package com.example.transport_service.service.impl;

import com.example.transport_service.domain.entity.TransportType;
import com.example.transport_service.repo.TransportTypeRepo;
import com.example.transport_service.service.TransportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportTypeServiceImpl implements TransportTypeService {
    private final TransportTypeRepo transportTypeRepo;
    @Override
    public TransportType createTransportType(TransportType transportType) {
        return transportTypeRepo.save(transportType);
    }

    @Override
    public void deleteTransportType(UUID transportTypeId) {

    }

    @Override
    public TransportType getTransportTypeByI(UUID transportTypeId) {
        return null;
    }

    @Override
    public List<TransportType> getAllTransportTypes() {
        return null;
    }

    @Override
    public TransportType updateTransportTypeById(UUID transportTypeId) {
        return null;
    }
}

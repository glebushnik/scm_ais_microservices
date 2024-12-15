package com.example.transport_service.service.impl;

import com.example.transport_service.domain.DTO.TransportTypeDTO;
import com.example.transport_service.domain.entity.TransportType;
import com.example.transport_service.domain.mapper.TransportMapper;
import com.example.transport_service.repo.TransportTypeRepo;
import com.example.transport_service.service.TransportTypeService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportTypeServiceImpl implements TransportTypeService {
    private final TransportTypeRepo transportTypeRepo;
    private final TransportMapper transportMapper;
    @Override
    public TransportType createTransportType(TransportTypeDTO transportType) {
        if (transportTypeRepo.existsByName(transportType.name())) {
            throw new IllegalArgumentException("Тип транспорта с именем " + transportType.name() + " уже существует.");
        }
        return transportTypeRepo.save(
                TransportType.builder()
                        .name(transportType.name())
                        .build()
        );
    }
    @Override
    public void deleteTransportType(UUID transportTypeId) {
        if (transportTypeRepo.findById(transportTypeId).isEmpty()) {
            throw new IllegalArgumentException("Типа транспорта с именем " + transportTypeId + " не существует.");
        }

        transportTypeRepo.deleteById(transportTypeId);
    }
    @Override
    public List<TransportTypeDTO> getAllTransportTypes() {
        return transportTypeRepo.findAll().stream().map(transportMapper::entityToDTO).collect(Collectors.toList());
    }
}

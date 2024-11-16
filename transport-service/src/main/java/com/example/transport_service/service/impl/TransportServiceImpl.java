package com.example.transport_service.service.impl;

import com.example.transport_service.domain.DTO.TransportDTO;
import com.example.transport_service.domain.DTO.ClientTransportDTO;
import com.example.transport_service.domain.entity.Transport;
import com.example.transport_service.domain.entity.TransportType;
import com.example.transport_service.domain.mapper.TransportMapper;
import com.example.transport_service.repo.TransportRepo;
import com.example.transport_service.repo.TransportTypeRepo;
import com.example.transport_service.service.TransportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {
    private final TransportRepo transportRepo;
    private final TransportTypeRepo transportTypeRepo;
    private final TransportMapper transportMapper;
    @Override
    public Transport createTransport(ClientTransportDTO transportDTO) {
        TransportType transportType = transportTypeRepo.findById(transportDTO.transportType())
                .orElseThrow(() -> new EntityNotFoundException("Тип транспорта с id " + transportDTO.transportType()+" не найден."));

        if (transportRepo.findByRegNumber(transportDTO.regNumber()).isPresent()) {
            throw new IllegalArgumentException("Транспорт с рег. номером " + transportDTO.regNumber() + " уже существует.");
        }

        Transport transport = Transport.builder()
                .driverId(transportDTO.driverId())
                .transportTypeId(transportType)
                .regNumber(transportDTO.regNumber())
                .volume(transportDTO.volume())
                .build();

        return transportRepo.save(transport);

    }

    @Override
    public TransportDTO getTransportById(UUID id) {
        return transportRepo.findById(id).map(transportMapper::entityToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Транспорт с ID " + id + " не найден."));
    }

    @Override
    public List<TransportDTO> getAllTransport() {
        return transportRepo.findAll().stream().map(
            transportMapper::entityToDTO
        ).collect(Collectors.toList());
    }

    @Override
    public TransportDTO updateTransportDriverById(UUID id, UUID driverId) {
        var transportOpt = transportRepo.findById(id);

        if (transportOpt.isEmpty()) {
            throw new EntityNotFoundException("Транспорт с ID " + id + " не найден.");
        }

        var transport = transportOpt.get();
        transport.setDriverId(driverId);
        return transportMapper.entityToDTO(transportRepo.save(transport));
    }


    @Override
    public void deleteTransportById(UUID id) {
        var transportOpt = transportRepo.findById(id);

        if (transportOpt.isEmpty()) {
            throw new EntityNotFoundException("Транспорт с ID " + id + " не найден.");
        }

        transportRepo.deleteById(id);
    }
}

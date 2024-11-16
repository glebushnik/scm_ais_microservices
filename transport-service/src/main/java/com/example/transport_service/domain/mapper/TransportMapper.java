package com.example.transport_service.domain.mapper;

import com.example.transport_service.domain.DTO.TransportDTO;
import com.example.transport_service.domain.entity.Transport;
import org.springframework.stereotype.Service;

@Service
public class TransportMapper {
    public TransportDTO entityToDTO(Transport transport) {
        return new TransportDTO(
                transport.getVolume(),
                transport.getRegNumber(),
                transport.getTransportTypeId().getName()
        );
    }
}

package com.example.transport_service.domain.mapper;

import com.example.transport_service.domain.DTO.TransportDTO;
import com.example.transport_service.domain.DTO.TransportResponseDTO;
import com.example.transport_service.domain.DTO.TransportTypeDTO;
import com.example.transport_service.domain.DTO.TransportTypeResponseDTO;
import com.example.transport_service.domain.entity.Transport;
import com.example.transport_service.domain.entity.TransportType;
import org.springframework.stereotype.Service;

@Service
public class TransportMapper {
    public TransportDTO entityToDTO(Transport transport) {
        return new TransportDTO(
                transport.getId(),
                transport.getVolume(),
                transport.getRegNumber(),
                transport.getTransportTypeId()!=null ? transport.getTransportTypeId().getName() : "null"
        );
    }

    public TransportTypeDTO entityToDTO(TransportType transportType) {
        return new TransportTypeDTO(
                transportType.getName()
                );
    }

    public TransportTypeResponseDTO entityToResponseDTO(TransportType transportType) {
        return new TransportTypeResponseDTO(
                transportType.getId(),
                transportType.getName()
        );
    }

    public TransportResponseDTO entityToResponseDTO(Transport transport) {
        return new TransportResponseDTO(
                transport.getId(),
                transport.getVolume(),
                transport.getRegNumber(),
                transport.getTransportTypeId()!=null ? transport.getTransportTypeId().getName() : "null"
        );
    }
}

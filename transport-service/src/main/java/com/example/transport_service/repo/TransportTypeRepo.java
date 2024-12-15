package com.example.transport_service.repo;

import com.example.transport_service.domain.entity.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface TransportTypeRepo extends JpaRepository<TransportType, UUID> {
    TransportType findTransportTypeByName(String name);
    Boolean existsByName(String name);
}


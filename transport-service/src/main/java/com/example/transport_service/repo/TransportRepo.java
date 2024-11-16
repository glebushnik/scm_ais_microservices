package com.example.transport_service.repo;

import com.example.transport_service.domain.entity.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TransportRepo extends JpaRepository<Transport, UUID> {
    Optional<Transport> findByRegNumber(String regNumber);
}

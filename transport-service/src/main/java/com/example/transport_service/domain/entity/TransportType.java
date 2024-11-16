package com.example.transport_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "transport_types")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransportType {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}

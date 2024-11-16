package com.example.transport_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "transport")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Transport {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="type_id")
    private TransportType transportTypeId;

    private String regNumber;

    private String volume;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;
}

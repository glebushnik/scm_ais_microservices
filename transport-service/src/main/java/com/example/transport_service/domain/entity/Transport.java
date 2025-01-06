package com.example.transport_service.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min=4, max=20)
    private String regNumber;

    @NotBlank
    @Size(min=4, max=20)
    private String volume;

    @Column(name = "user_id")
    private UUID userId;
}

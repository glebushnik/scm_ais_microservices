package com.glebushnik.warehouse_service.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @GeneratedValue
    @Id
    private UUID id;

    @NotBlank
    @Size(min = 4, max = 20)
    private String name;

    @NotBlank
    @Size(min = 6, max = 8)
    private String volume;

    private Integer quantity;

    private Boolean isFragile;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
}
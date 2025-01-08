package com.glebushnik.warehouse_service.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouses")
public class Warehouse {
    @GeneratedValue
    @Id
    private UUID id;

    @NotBlank
    @Size(min = 5)
    private String warehouseName;

    private UUID companyId;

    private String coords;

    @NotBlank
    @Size(min = 10)
    private String address;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @ElementCollection
    @CollectionTable(name = "warehouse_transport_ids", joinColumns = @JoinColumn(name = "warehouse_id"))
    @Column(name = "transport_id")
    private List<UUID> transportIds;

    @ElementCollection
    @CollectionTable(name = "warehouse_user_ids", joinColumns = @JoinColumn(name = "warehouse_id"))
    @Column(name = "user_id")
    private List<UUID> userIds;
}
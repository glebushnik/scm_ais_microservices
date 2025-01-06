package com.glebushnik.warehouse_service.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateItemDTO(
        @NotBlank
        @Size(min = 4, max = 20)
        String name,
        @NotBlank
        @Size(min = 6, max = 8)
        String volume,
        @NotBlank
        Integer quantity,
        @NotBlank
        Boolean isFragile
) {
}

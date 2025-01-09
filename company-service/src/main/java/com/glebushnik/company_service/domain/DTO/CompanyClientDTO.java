package com.glebushnik.company_service.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CompanyClientDTO(
        @NotBlank
        @Size(min=4)
        String companyName,
        UUID owner
) {
}

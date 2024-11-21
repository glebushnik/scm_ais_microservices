package com.glebushnik.user_service.domain.dto.user.UserResponseDTO;

import com.glebushnik.user_service.domain.entity.User;
import com.glebushnik.user_service.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private Role role;
}

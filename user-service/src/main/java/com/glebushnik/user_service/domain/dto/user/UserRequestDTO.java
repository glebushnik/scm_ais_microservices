package com.glebushnik.user_service.domain.dto.user;

import com.glebushnik.user_service.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private Role role;
    private String password;
}

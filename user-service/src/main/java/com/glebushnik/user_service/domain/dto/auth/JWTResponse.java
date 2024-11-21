package com.glebushnik.user_service.domain.dto.auth;

import com.glebushnik.user_service.domain.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JWTResponse {
    private String accessToken;
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private Role role;
}

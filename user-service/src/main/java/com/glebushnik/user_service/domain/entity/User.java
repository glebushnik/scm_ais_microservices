package com.glebushnik.user_service.domain.entity;

import com.glebushnik.user_service.domain.enums.Role;
import com.glebushnik.user_service.validation.StrongPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(min=4, max=20)
    private String firstName;

    @NotBlank
    @Size(min=4, max=20)
    private String lastName;

    @Email
    private String email;

    @NotBlank
    @Size(min=8, max=20)
    @StrongPassword
    private String password;

    @NotBlank
    @Size(min=4, max=20)
    private String patronymic;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.glebushnik.user_service.domain.mapper;

import com.glebushnik.user_service.domain.dto.user.UserRequestDTO;
import com.glebushnik.user_service.domain.dto.user.UserResponseDTO.UserResponseDTO;
import com.glebushnik.user_service.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public UserResponseDTO eToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .role(user.getRole())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .build();
    }
    public User mapUserEntityWithRequestDTO(User user, UserRequestDTO requestDTO) {
        user.setFirstName(requestDTO.getFirstName() != null ? requestDTO.getFirstName() : user.getFirstName());
        user.setLastName(requestDTO.getLastName() != null ? requestDTO.getLastName() : user.getLastName());
        user.setPatronymic(requestDTO.getPatronymic() != null ? requestDTO.getPatronymic() : user.getPatronymic());
        user.setEmail(requestDTO.getEmail() != null ? requestDTO.getEmail() : user.getEmail());
        user.setRole(requestDTO.getRole() != null ? requestDTO.getRole() : user.getRole());
        user.setPassword(requestDTO.getPassword() != null ? passwordEncoder.encode(requestDTO.getPassword()) : user.getPassword());
        return user;
    }
}

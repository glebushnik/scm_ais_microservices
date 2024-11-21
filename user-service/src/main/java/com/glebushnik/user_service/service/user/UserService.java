package com.glebushnik.user_service.service.user;

import com.glebushnik.user_service.domain.dto.user.UserRequestDTO;
import com.glebushnik.user_service.domain.dto.user.UserResponseDTO.UserResponseDTO;
import com.glebushnik.user_service.domain.mapper.UserMapper;
import com.glebushnik.user_service.repo.RefreshTokenRepo;
import com.glebushnik.user_service.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllUsers() {
        return userRepo.findAll().stream().map(userMapper::eToDTO).collect(Collectors.toList());
    }


    public UserResponseDTO getUserById(UUID userId) {
        return userMapper.eToDTO(userRepo.findById(userId).orElseThrow(()
                -> new EntityNotFoundException(String.format("Пользователь с id : %d не найден",userId))));

    }


    public UserResponseDTO updateUserById(UUID userId, UserRequestDTO requestDTO) {
        var user = userRepo.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Пользователь с id : %d не найден", userId))
        );

        userMapper.mapUserEntityWithRequestDTO(user, requestDTO);

        return userMapper.eToDTO(userRepo.save(user));
    }


    public void deleteUserById(UUID userId) {
        var user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с id : %d не найден", userId)));
        var refresh = user.getRefreshToken();
        refreshTokenRepo.delete(refresh);
        userRepo.delete(user);
    }

    public UserResponseDTO getCurrentUser(String userNameFromAccess) {
        return userMapper.eToDTO(userRepo.findByEmail(userNameFromAccess).get());
    }
}

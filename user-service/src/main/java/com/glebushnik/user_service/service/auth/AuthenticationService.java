package com.glebushnik.user_service.service.auth;

import com.glebushnik.user_service.domain.dto.auth.*;
import com.glebushnik.user_service.domain.entity.User;
import com.glebushnik.user_service.domain.enums.Role;
import com.glebushnik.user_service.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .patronymic(request.getPatronymic())
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user.getEmail());
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();


        var jwtToken = jwtService.generateToken(user.getEmail());
        return  AuthenticationResponse.builder().token(jwtToken).build();
    }

    public ChangePassResponse changePassword(ChangePassRequest request) throws EntityNotFoundException {
        var user = userRepo.findById(request.getUserId()).orElseThrow(
                ()->new IllegalArgumentException(String.format("Пользователь с id : %d не найден.", request.getUserId()))
        );

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        userRepo.save(user);
        return ChangePassResponse.builder()
                .newPassword(request.getPassword())
                .build();
    }

    public String validateToken(String token){
        jwtService.validateToken(token);
        return "Token is valid";
    }
}

package com.glebushnik.user_service.controller;

import com.glebushnik.user_service.domain.dto.auth.*;
import com.glebushnik.user_service.domain.entity.RefreshToken;
import com.glebushnik.user_service.service.auth.AuthenticationService;
import com.glebushnik.user_service.service.auth.JwtService;
import com.glebushnik.user_service.service.auth.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Получаем JWTResponse, access token и refresh token",
            tags = { "users", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = JWTResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        try {
            var accessToken = authenticationService.register(request).getToken();
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
            return ResponseEntity.ok().body(
                    JWTResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken.getToken())
                            .role(refreshToken.getUser().getRole())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Получаем JWTResponse, access token и refresh token",
            tags = { "users", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = JWTResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        try {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());
            return ResponseEntity.ok().body(
                    JWTResponse.builder()
                            .accessToken(authenticationService.authenticate(request).getToken())
                            .refreshToken(refreshToken.getToken())
                            .role(refreshToken.getUser().getRole())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refreshtoken")
    @Operation(
            summary = "Получить refresh token",
            description = "Получаем refresh token",
            tags = { "users", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = JWTResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            RefreshToken refreshToken = refreshTokenService.findByToken(request.getToken()).get();
            String userName = refreshToken.getUser().getEmail();
            return ResponseEntity.ok().body(
                    JWTResponse.builder()
                            .refreshToken(refreshTokenService.updateRefreshToken(refreshToken.getToken()).getToken())
                            .accessToken(jwtService.generateToken(userName))
                            .role(refreshToken.getUser().getRole())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/change-password")
    @Operation(
            summary = "Сменить пароль",
            description = "Получаем ChangePasswordResponse",
            tags = { "users", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ChangePassResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changePasswordById(@RequestBody ChangePassRequest request) {
        try {
            return ResponseEntity.ok().body(authenticationService.changePassword(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validate-token")
    @Operation(
            summary = "Валидация токена",
            description = "Получаем ответ: валидный токен или нет",
            tags = { "users", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ChangePassResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        try {
            return ResponseEntity.ok().body(authenticationService.validateToken(token));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

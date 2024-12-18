package com.glebushnik.user_service.controller;

import com.glebushnik.user_service.domain.dto.user.UserRequestDTO;
import com.glebushnik.user_service.domain.dto.user.UserResponseDTO.UserResponseDTO;
import com.glebushnik.user_service.domain.dto.user.UserTransportAssignmentRequest;
import com.glebushnik.user_service.service.auth.JwtService;
import com.glebushnik.user_service.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    @GetMapping("/{userId}")
    @Operation(
            summary = "Получить данные о пользователе по Id",
            description = "Получаем UserResponseDTO",
            tags = { "users", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/current")
    @Operation(
            summary = "Получить данные о текущем пользователе",
            description = "Получаем UserResponseDTO",
            tags = { "users", "get", "auth" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<?> getCurrentUserInfo(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring(7);
            String usernameFromAccess = jwtService.extractUserName(token);
            return ResponseEntity.ok().body(userService.getCurrentUser(usernameFromAccess));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получить всех пользователей",
            description = "Получаем список UserResponseDTO",
            tags = {"users", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserResponseDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserResponseDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Удалить пользователя по Id",
            description = "Удаляем пользователя по Id. Доступно только для админа.",
            tags = {"admin", "users", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<String> deleteUserById(@PathVariable UUID userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok().body(String.format("User with id : %s deleted.", userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Обновить данные пользователя по его Id и UserRequestDTO",
            description = "Получаем UserResponseDTO.",
            tags = { "admin", "users", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<?> updateUserById(@PathVariable UUID userId, @RequestBody UserRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok().body(userService.updateUserById(userId, requestDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add-car")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Связать пользователя с транспортом.",
            description = "Получаем сообщение успеха.",
            tags = { "admin", "users", "put"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь или транспорт не найден",
                    content = { @Content(schema = @Schema()) }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = { @Content(schema = @Schema()) }
            )
    })
    public ResponseEntity<?> addTransportToUser(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для связывания пользователя с транспортом",
                    required = true
            ) UserTransportAssignmentRequest request
    )  {
        try {
            Map<String, Object> transportAssignment = new HashMap<>();
            var userId = request.getUserId();
            transportAssignment.put("userId", request.getUserId());
            transportAssignment.put("transportIds", request.getTransportIds());
            userService.addTransportToUser(userId, transportAssignment);
            return ResponseEntity.ok().body("Пользователь: " + userId + " связан с транспортом: " + transportAssignment.get("transportIds"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

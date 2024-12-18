package com.glebushnik.user_service.domain.dto.auth;

import com.glebushnik.user_service.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Поле firstname не может быть пустым")
    private String firstName;

    @NotBlank(message = "Поле lastname не может быть пустым")
    private String lastName;

    @NotBlank(message = "Поле patronymic не может быть пустым")
    private String patronymic;

    @NotBlank(message = "Поле email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Поле password не может быть пустым")
    @Size(min=8, max=20)
    @StrongPassword
    private String password;
}

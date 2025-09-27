package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Запрос на аутентификацию и авторизацию клиента(получение JWT токена),содержит логин и пароль")
public record AuthenticateRequestDto(

    @Schema(description = "Логин пользователя (email)")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Email должен быть валидным")
    String login,

    @Schema(description = "Пароль пользователя(состоит только из цифр)")
    @Pattern(regexp = "\\d+", message = "Пароль должен состоять из цифр")
    String password
) {

}

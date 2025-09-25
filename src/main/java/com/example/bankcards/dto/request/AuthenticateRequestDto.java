package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на аутентификацию и авторизацию клиента(получение JWT токена),содержит логин и пароль")
public record AuthenticateRequestDto(

    @Schema(description = "Логин клиента")
    @NotNull
    String login,

    @Schema(description = "Пароль клиента")
    @NotNull
    String password
) {

}

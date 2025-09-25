package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

@Schema(description = "Запрос на создание пользователя")
public record RegistrationUserRequestDto(

    @Schema(description = "Логин пользователя (email)")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Email должен быть валидным")
    String login,

    @Schema(description = "Пароль пользователя(состоит только из цифр)")
    @Pattern(regexp = "\\d+")
    String password,

    @Schema(description = "Роль пользователя")
    @Pattern(regexp = "^(ADMIN|USER)$",
        message = "Роль может быть только 'ADMIN' или 'USER'")
    String userRole,

    @Schema(description = "Имя пользователя")
    @NotBlank
    String firstname,

    @Schema(description = "Фамилия пользователя")
    @NotBlank
    String lastname,

    @Schema(description = "Дата рождения пользователя")
    @NotNull
    LocalDate birthDate,

    @Schema(description = "Телефонный номер пользователя")
    @Pattern(regexp = "\\+((375|7)\\d{9})")
    String phoneNumber
) {

}





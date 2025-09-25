package com.example.bankcards.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record RegistrationUserRequestDto(

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Email должен быть валидным")
    String login,

    @Pattern(regexp = "\\d+")
    String password,

    @NotBlank
    String userRole,

    @NotBlank
    String firstname,

    @NotBlank
    String lastname,

    @NotNull
    LocalDate birthDate,

    @Pattern(regexp = "\\+((375|7)\\d{9})")
    String phoneNumber
) {

}





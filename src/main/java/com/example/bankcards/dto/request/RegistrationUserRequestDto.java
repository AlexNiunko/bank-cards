package com.example.bankcards.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RegistrationUserRequestDto(

    @NotNull
    String login,

    @NotNull
    String password,

    @NotNull
    String userRole,

    @NotNull
    String firstname,

    @NotNull
    String lastname,

    @NotNull
    LocalDate birthDate,

    @NotNull
    String phoneNumber
) {

}





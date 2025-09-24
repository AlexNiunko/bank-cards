package com.example.bankcards.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthenticateRequestDto(

    @NotNull
    String login,

    @NotNull
    String password
) {

}

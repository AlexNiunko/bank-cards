package com.example.bankcards.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateUserStatusRequestDto(

    @NotNull
    Long userId,

    @Pattern(regexp = "ACTIVE|BLOCKED")
    String status

) {

}

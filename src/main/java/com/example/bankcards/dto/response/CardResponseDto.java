package com.example.bankcards.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CardResponseDto(
    @NotNull
    Long cardId,

    @NotNull
    @NotBlank
    String firstname,

    @NotNull
    @NotBlank
    String lastname
) {

}

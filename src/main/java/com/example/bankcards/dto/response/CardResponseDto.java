package com.example.bankcards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "Ответ на запрос операции с картами")
public record CardResponseDto(

    @Schema(description = "Идентификатор карты пользователя")
    @NotNull
    Long cardId,

    @Schema(description = "Имя пользователя")
    @NotBlank
    String firstname,

    @Schema(description = "Фамилия пользователя")
    @NotBlank
    String lastname
) {

}

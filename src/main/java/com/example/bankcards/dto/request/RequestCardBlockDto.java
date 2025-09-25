package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на блокировку карты")
public record RequestCardBlockDto(

    @Schema(description = "Идентификатор пользователя")
    @NotNull
    Long userId,

    @Schema(description = "Идентификатор карты")
    @NotNull
    Long cardId
) {

}

package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на получение баланса для карты пользователя")
public record BalanceRequestDto(

    @Schema(description = "Идентификатор пользователя")
    @NotNull
    Long userId,

    @Schema(description = "Идентификатор банковской карты пользователя")
    @NotNull
    Long cardId
) {

}

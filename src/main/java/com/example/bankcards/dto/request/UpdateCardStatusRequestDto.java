package com.example.bankcards.dto.request;

import com.example.bankcards.entity.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Запрос на обновление статуса карты")
public record UpdateCardStatusRequestDto(

    @Schema(description = "Идентификатор карты")
    @NotNull
    Long cardId,

    @Schema(description = "Устанавливаемый статус карты")
    @Pattern(regexp = "^(ACTIVE|BLOCKED|EXPIRED)$",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Разрешены только: ACTIVE, BLOCKED, EXPIRED")
    CardStatus status
) {

}

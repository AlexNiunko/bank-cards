package com.example.bankcards.dto.request;

import com.example.bankcards.entity.CardStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateCardStatusRequestDto(
    @NotNull
    Long cardId,

    @Pattern(regexp = "^(ACTIVE|BLOCKED|EXPIRED)$",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Разрешены только: ACTIVE, BLOCKED, EXPIRED")
    CardStatus status
) {

}

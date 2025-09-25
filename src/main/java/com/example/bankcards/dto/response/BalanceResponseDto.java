package com.example.bankcards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

@Schema(description = "Ответ на запрос получение баланса карты пользователя")
@Builder
public record BalanceResponseDto(

    @Schema(description = "Идентификатор карты пользователя")
    @NotNull
    Long cardId,

    @Schema(description = "Баланс карты пользователя")
    @NotNull
    BigDecimal amount
) {

}

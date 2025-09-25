package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Запрос на перевод денег с одной карты(отправитель) на другую(получатель) конкретного пользователя")
public record TransferRequestDto(

    @Schema(description = "Идентификатор пользователя(владельца карт)")
    @NotNull
    Long userId,

    @Schema(description = "Идентификатор карты отправителя")
    @NotNull
    Long fromCardId,

    @Schema(description = "Идентификатор карты получателя")
    @NotNull
    Long toCardId,

    @Schema(description = "Сумма перевода")
    @NotNull
    BigDecimal amount
) {

}

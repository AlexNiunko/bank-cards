package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

@Schema(description = "Запрос на получение карт по фильтру")
public record FilterCardRequestDto(

    @Schema(description = "Максимальное значение баланса карты")
    BigDecimal maxBalance,

    @Schema(description = "Минимальное значение баланса карты")
    BigDecimal minBalance,

    @Schema(description = "Статус карты")
    @Pattern(regexp = "^(ACTIVE|BLOCKED|EXPIRED)$",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Разрешены только значения: ACTIVE,BLOCKED,EXPIRED")
    String cardStatus,

    @Schema(description = "Платежная система карты")
    @Pattern(regexp = "^(VISA|MASTERCARD|MIR)$",
        message = "Разрешены только: VISA, MASTERCARD, MIR")
    String paymentSystem,

    @Schema(description = "Валюта карты")
    @Pattern(regexp = "^(RUB|USD|EUR)$",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Разрешены только валюты: RUB, USD, EUR")
    String currency
) {

}

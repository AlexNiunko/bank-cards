package com.example.bankcards.dto.request;

import com.example.bankcards.entity.CurrencyType;
import com.example.bankcards.entity.PaymentSystem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Schema(description = "Запрос на создание карты для пользователя")
public record CreateCardRequestDto(

    @Schema(description = "Идентификатор пользователя")
    @NotNull
    Long userId,

    @Schema(description = "Платежная система карты")
    @Pattern(regexp = "^(VISA|MASTERCARD|MIR)$",
        message = "Разрешены только: VISA, MASTERCARD, MIR")
    PaymentSystem paymentSystem,

    @Schema(description = "Дата-время по которое карта действительна")
    @NotNull
    LocalDateTime expirationTime,

    @Schema(description = "Валюта карты")
    @Pattern(regexp = "^(RUB|USD|EUR)$",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Разрешены только валюты: RUB, USD, EUR")
    CurrencyType currency,

    @Schema(description = "Номер карты")
    @Pattern(regexp = "^\\d{16}$", message = "Номер карты должен содержать 16 цифр")
    String cardNumber

) {

}

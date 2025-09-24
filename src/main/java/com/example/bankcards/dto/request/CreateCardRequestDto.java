package com.example.bankcards.dto.request;

import com.example.bankcards.entity.CurrencyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public record CreateCardRequestDto(

    @NotNull
    Long userId,

    @Pattern(regexp = "^(VISA|MASTERCARD|MIR)$",
        message = "Разрешены только: VISA, MASTERCARD, MIR")
    String paymentSystem,

    @NotNull
    LocalDateTime expirationTime,

    @Pattern(regexp = "^(RUB|USD|EUR)$",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Разрешены только валюты: RUB, USD, EUR")
    CurrencyType currency,

    @Pattern(regexp = "^\\d{16}$", message = "Номер карты должен содержать 16 цифр")
    String cardNumber

) {

}

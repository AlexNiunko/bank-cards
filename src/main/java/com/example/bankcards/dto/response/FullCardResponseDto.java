package com.example.bankcards.dto.response;

import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.CurrencyType;
import com.example.bankcards.entity.PaymentSystem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record FullCardResponseDto(
    Long cardId,
    Long userId,
    PaymentSystem paymentSystem,
    String cardNumber,
    BigDecimal balance,
    LocalDateTime expirationTime,
    CardStatus status,
    CurrencyType currency

) {

}

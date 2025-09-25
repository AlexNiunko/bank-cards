package com.example.bankcards.dto.response;

import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.CurrencyType;
import com.example.bankcards.entity.PaymentSystem;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Schema(description = "Ответ содержащий полную информацию о карте пользователя")
@Builder
public record FullCardResponseDto(

    @Schema(description = "Идентификатор карты")
    Long cardId,

    @Schema(description = "Идентификатор владельца карты")
    Long userId,

    @Schema(description = "Платежная система карты")
    PaymentSystem paymentSystem,

    @Schema(description = "Номер карты")
    String cardNumber,

    @Schema(description = "Баланс карты")
    BigDecimal balance,

    @Schema(description = "Дата-время по которое карта действительна")
    LocalDateTime expirationTime,

    @Schema(description = "Статус карты")
    CardStatus status,

    @Schema(description = "Валюта карты")
    CurrencyType currency

) {

}

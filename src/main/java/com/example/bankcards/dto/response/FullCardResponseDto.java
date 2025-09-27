package com.example.bankcards.dto.response;

import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.CurrencyType;
import com.example.bankcards.entity.PaymentSystem;
import com.example.bankcards.util.masking.MaskType;
import com.example.bankcards.util.masking.Masked;
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

    @Masked(type = MaskType.CREDIT_CARD)
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

package com.example.bankcards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Schema(description = "Ответ на запрос перевода между картами пользователя")
@Builder
public record TransferResponseDto(

    @Schema(description = "Актуальный баланс карты отправителя")
    BigDecimal fromBalance,

    @Schema(description = "Актуальный баланс карты получателя")
    BigDecimal toBalance,

    @Schema(description = "Сумма перевода")
    BigDecimal amount
) {

}

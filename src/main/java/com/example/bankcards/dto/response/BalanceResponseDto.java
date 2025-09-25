package com.example.bankcards.dto.response;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record BalanceResponseDto(

    @NotNull
    Long cardId,

    @NotNull
    BigDecimal amount
) {

}

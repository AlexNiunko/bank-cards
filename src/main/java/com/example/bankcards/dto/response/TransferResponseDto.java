package com.example.bankcards.dto.response;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record TransferResponseDto(

    BigDecimal fromBalance,
    BigDecimal toBalance,
    BigDecimal amount
) {

}

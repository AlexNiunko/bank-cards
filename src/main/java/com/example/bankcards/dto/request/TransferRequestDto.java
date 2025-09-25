package com.example.bankcards.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransferRequestDto(

    @NotNull
    Long fromCardId,

    @NotNull
    Long toCardId,

    @NotNull
    BigDecimal amount
) {

}

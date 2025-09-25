package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Запрос на обновление статуса пользователя")
public record UpdateUserStatusRequestDto(

    @Schema(description = "Идентификатор пользователя")
    @NotNull
    Long userId,

    @Schema(description = "Устанавливаемый статус пользователя")
    @Pattern(regexp = "ACTIVE|BLOCKED")
    String status

) {

}

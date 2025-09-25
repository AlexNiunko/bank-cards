package com.example.bankcards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Ответ на операции с пользователем")
public record UserResponseDto(

    @Schema(description = "Идентификатор пользователя")
    Long userId,

    @Schema(description = "Имя пользователя")
    String firstname,

    @Schema(description = "Фамилия пользователя")
    String lastName
) {

}

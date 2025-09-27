package com.example.bankcards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Ответ на запрос создания карты пользователя")
@Builder
public record CreateCardResponseDto(

    @Schema(description = "Идентификатор карты пользователя")
    Long cardId,

    @Schema(description = "Имя пользователя")
    String firstName,

    @Schema(description = "Фамилия пользователя")
    String lastName
) {

}

package com.example.bankcards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Ответ на запрос блокирования карты пользователя")
@Builder
public record CardResponseBlockDto(

    @Schema(description = "Идентификатор карты пользователя")
    Long cardId,

    @Schema(description = "Идентификатор запроса на блокировку карты пользователя")
    Long requestId
) {

}

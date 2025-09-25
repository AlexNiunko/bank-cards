package com.example.bankcards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос аутентификации и авторизацию пользователя")
public record AuthenticateResponseDto(

    @Schema(description = "JWT пользователя (необходимо установить в хедер Authorization -- Bearer )")
    String message
) {

}

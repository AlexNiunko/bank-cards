package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на получение карт постранично")
public record PageableCardRequest(

    @Schema(description = "Номер страницы")
    Integer pageNumber,

    @Schema(description = "Количество записей на странице")
    Integer pageSize
) {

}

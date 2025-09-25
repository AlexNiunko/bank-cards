package com.example.bankcards.dto.response;

import lombok.Builder;

@Builder
public record CardResponseBlockDto(
    Long cardId,
    Long requestId
) {

}

package com.example.bankcards.dto.response;

import lombok.Builder;

@Builder
public record CreateCardResponseDto (
    Long cardId,
    String firstName,
    String lastName
){

}

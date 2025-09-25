package com.example.bankcards.dto.response;

import lombok.Builder;

@Builder
public record UserResponseDto(

    Long userId,
    String firstname,
    String lastName
) {

}

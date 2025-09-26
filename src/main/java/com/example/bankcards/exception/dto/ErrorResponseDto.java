package com.example.bankcards.exception.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record ErrorResponseDto(
    String message,
    List<String> errors
) {

}

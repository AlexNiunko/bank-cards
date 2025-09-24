package com.example.bankcards.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record GenerateTokenDto(
    Long userId,
    String login,
    List<String> roles
) {

}

package com.example.bankcards.security.authentication.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record GenerateTokenDto(
    Long userId,
    String login,
    List<String> roles
) {

}

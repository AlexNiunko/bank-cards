package com.example.bankcards.dto.response;

import com.example.bankcards.entity.ProfileStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record FullUserResponseDto(

    Long userId,
    String login,
    List<String>roles,
    ProfileStatus status,
    String firstname,
    String lastname,
    LocalDate birthDate,
    String phoneNumber
) {

}

package com.example.bankcards.dto.response;

import com.example.bankcards.entity.ProfileStatus;
import com.example.bankcards.util.MaskType;
import com.example.bankcards.util.Masked;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Schema(description = "Ответ содержащий полную информацию о пользователе")
@Builder
public record FullUserResponseDto(

    @Schema(description = "Идентификатор пользователя")
    Long userId,

    @Masked(type = MaskType.EMAIL)
    @Schema(description = "Логин пользователя")
    String login,

    @Schema(description = "Список ролей пользователя")
    List<String>roles,

    @Schema(description = "Статус пользователя")
    ProfileStatus status,

    @Schema(description = "Имя пользователя")
    String firstname,

    @Schema(description = "Фамилия пользователя")
    String lastname,

    @Schema(description = "Дата рождения пользователя")
    LocalDate birthDate,

    @Masked(type = MaskType.PHONE)
    @Schema(description = "Телефонный номер пользователя")
    String phoneNumber
) {

}

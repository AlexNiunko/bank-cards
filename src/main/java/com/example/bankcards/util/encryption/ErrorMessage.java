package com.example.bankcards.util.encryption;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

    public static final String VALIDATION_ERROR = "Ошибка валидации запроса";
    public static final String AUTHENTICATION_ERROR = "Ошибка аутентификации";
    public static final String BUSINESS_ERROR = "Ошибка бизнес-правила";
    public static final String ENCRYPTION_ERROR = "Ошибка бизнес-правила";

}

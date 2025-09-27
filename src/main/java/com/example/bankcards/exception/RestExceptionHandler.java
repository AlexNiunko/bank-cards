package com.example.bankcards.exception;

import static com.example.bankcards.util.encryption.ErrorMessage.AUTHENTICATION_ERROR;
import static com.example.bankcards.util.encryption.ErrorMessage.BUSINESS_ERROR;
import static com.example.bankcards.util.encryption.ErrorMessage.ENCRYPTION_ERROR;
import static com.example.bankcards.util.encryption.ErrorMessage.VALIDATION_ERROR;

import com.example.bankcards.exception.dto.ErrorResponseDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleValidationErrors(MethodArgumentNotValidException ex) {

        Map <String,String> errors=new HashMap<>();

        ex.getBindingResult()
            .getFieldErrors()
            .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        var collect = errors.entrySet().stream()
            .map(entry -> String.join(": ", entry.getKey(), entry.getValue()))
            .toList();

        return getResponseDto(VALIDATION_ERROR, collect);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ErrorResponseDto handleAccessErrors(AuthenticationCredentialsNotFoundException ex) {

        return getResponseDto(AUTHENTICATION_ERROR, List.of(ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ErrorResponseDto handleBusinessErrors(BusinessException ex) {

        return getResponseDto(BUSINESS_ERROR, List.of(ex.getMessage()));
    }

    @ExceptionHandler(EncryptionException.class)
    public ErrorResponseDto handleEncryptionErrors(EncryptionException ex) {

        return getResponseDto(ENCRYPTION_ERROR, List.of(ex.getMessage()));
    }


    private ErrorResponseDto getResponseDto(String message, List<String> collect) {
        return ErrorResponseDto.builder()
            .message(message)
            .errors(collect)
            .build();
    }
}

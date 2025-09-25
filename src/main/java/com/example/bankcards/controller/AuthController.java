package com.example.bankcards.controller;

import com.example.bankcards.dto.AppError;
import com.example.bankcards.dto.ErrorResponseDto;
import com.example.bankcards.dto.request.AuthenticateRequestDto;
import com.example.bankcards.dto.response.AuthenticateResponseDto;
import com.example.bankcards.security.authentication.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/bank-rest")
@RequiredArgsConstructor
@Tag(name = "Аутентификация и авторизация клиента", description = "Предоставляет эндпоинт для аутентификации и авторизации клинта")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Аутентификация и авторизация клиента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Токен доступа успешно получен"),
        @ApiResponse(responseCode = "400", description = "Некорректный запрос",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @PostMapping("/auth")
    public AuthenticateResponseDto authenticate(@RequestBody @Valid AuthenticateRequestDto dto) {
        return authService.authenticate(dto);
    }

}


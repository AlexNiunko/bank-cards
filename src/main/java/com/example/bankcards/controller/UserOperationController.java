package com.example.bankcards.controller;

import com.example.bankcards.dto.request.BalanceRequestDto;
import com.example.bankcards.dto.request.RequestCardBlockDto;
import com.example.bankcards.dto.request.TransferRequestDto;
import com.example.bankcards.dto.response.BalanceResponseDto;
import com.example.bankcards.dto.response.CardResponseBlockDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.dto.response.TransferResponseDto;
import com.example.bankcards.exception.dto.ErrorResponseDto;
import com.example.bankcards.service.UserOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank-rest/user-operation")
@RequiredArgsConstructor
@Tag(name = "Пользовательские операции с картами", description = "Предоставляет эндпоинты для операций с картами конкретного пользователя")
public class UserOperationController {

    private final UserOperationService userOperationService;

    @Operation(summary = "Получить все карты пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карты успешно получены"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для получения карт не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/get-user-cards")
    public List<FullCardResponseDto> getUserCard(@RequestBody Long userId) {
        return userOperationService.getUserCards(userId);
    }

    @Operation(summary = "Отправить запрос на блокировку карты")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Запрос успешно отправлен"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для отправки запроса на блокировку не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/request-block-card")
    public CardResponseBlockDto requestBlockCard(@RequestBody @Valid RequestCardBlockDto dto) {
        return userOperationService.requestBlockCard(dto);
    }

    @Operation(summary = "Перевод между картами пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Перевод завершился успешно"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для перевода не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/transfer")
    public TransferResponseDto transfer(@RequestBody @Valid TransferRequestDto dto) {
        return userOperationService.transfer(dto);
    }

    @Operation(summary = "Получить баланс карты")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Баланс карты успешно получены"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для получения баланса карты не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/balance")
    public BalanceResponseDto getBalance(@RequestBody @Valid BalanceRequestDto dto) {
        return userOperationService.getBalance(dto);
    }

}

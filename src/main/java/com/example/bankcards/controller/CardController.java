package com.example.bankcards.controller;

import com.example.bankcards.dto.request.CreateCardRequestDto;
import com.example.bankcards.dto.request.FilterCardRequestDto;
import com.example.bankcards.dto.request.PageableCardRequest;
import com.example.bankcards.dto.request.UpdateCardStatusRequestDto;
import com.example.bankcards.dto.response.CardResponseDto;
import com.example.bankcards.dto.response.CreateCardResponseDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.exception.dto.ErrorResponseDto;
import com.example.bankcards.repository.criteria.CardCriteriaRepositoryImpl;
import com.example.bankcards.service.CardService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-rest/card")
@Tag(name = "Управление картами", description = "Предоставляет эндпоинты для управления картами")
public class CardController {

    private final CardService cardService;

    private final CardCriteriaRepositoryImpl criteriaRepository;

    @Operation(summary = "Создать новую карту")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карта успешно создана"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для создания карты не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/create")
    public CreateCardResponseDto createCard(
        @RequestHeader("Authorization") String headerValue,
        @RequestBody @Valid CreateCardRequestDto dto) {
        return cardService.createCard(dto);
    }

    @Operation(summary = "Установить статус для данной карты(заблокировать или активировать)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Статус установлен"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для блокирования карты не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/update")
    public CardResponseDto updateCardStatus(@RequestBody @Valid UpdateCardStatusRequestDto dto) {

        return cardService.updateCardStatus(dto);
    }


    @Operation(summary = "Удалить данную карту")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карта удалена"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для активирования карты не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete")
    public CardResponseDto deleteCard(@RequestBody @Valid Long cardId) {

        return cardService.deleteCard(cardId);
    }

    @Operation(summary = "Получить все карты")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карты получены"),
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
    @GetMapping("/get-all-cards")
    public List<FullCardResponseDto> getAllCards() {
        return cardService.getAllCards();
    }

    @Operation(summary = "Получить все карты по фильтру")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карты получены"),
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
    @PostMapping("/get-all-cards-by-filter")
    public List<FullCardResponseDto> getAllCardsByFilter(@RequestBody @Valid FilterCardRequestDto dto) {
        return criteriaRepository.getCardsByFilter(dto);
    }

    @Operation(summary = "Получить все карты по фильтру")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карты получены"),
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
    @PostMapping("/get-all-cards-using-page")
    public List<FullCardResponseDto> getAllCardsByPage(@RequestBody @Valid PageableCardRequest dto) {
        return cardService.getAllCardsUsingPageable(dto);
    }
}

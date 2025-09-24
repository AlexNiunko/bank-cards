package com.example.bankcards.controller;

import com.example.bankcards.dto.ErrorResponseDto;
import com.example.bankcards.dto.request.ActivateCardRequestDto;
import com.example.bankcards.dto.request.BlockCardRequestDto;
import com.example.bankcards.dto.request.CreateCardRequestDto;
import com.example.bankcards.dto.request.DeleteCardRequestDto;
import com.example.bankcards.dto.response.ActivateCardResponseDto;
import com.example.bankcards.dto.response.BlockCardResponseDto;
import com.example.bankcards.dto.response.CreateCardResponseDto;
import com.example.bankcards.dto.response.DeleteCardResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank-rest/card")
public class CardController {

    @Operation(summary = "Создать новую карту")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карта успешно создана"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс дял создания карты не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @PostMapping("/create")
    public CreateCardResponseDto createCard(CreateCardRequestDto dto){
        CreateCardResponseDto response=new CreateCardResponseDto();

        return response;
    }

    @Operation(summary = "Заблокировать данную карту")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карта заблокирована"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для блокирования карты не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @PostMapping("/block")
    public BlockCardResponseDto blockCard(BlockCardRequestDto dto){
        BlockCardResponseDto response=new BlockCardResponseDto();

        return response;
    }

    @Operation(summary = "Активировать данную карту")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карта активирована"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для активирования карты не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @PostMapping("/activate")
    public ActivateCardResponseDto activateCard(ActivateCardRequestDto dto){
        ActivateCardResponseDto response=new ActivateCardResponseDto();

        return response;
    }


    @Operation(summary = "Удалить данную карту")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карта удалена"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для активирования карты не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @DeleteMapping("/delete")
    public DeleteCardResponseDto createCard(DeleteCardRequestDto dto){
        DeleteCardResponseDto response=new DeleteCardResponseDto();

        return response;
    }

}

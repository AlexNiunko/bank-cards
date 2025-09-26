package com.example.bankcards.controller;

import com.example.bankcards.dto.request.RegistrationUserRequestDto;
import com.example.bankcards.dto.request.UpdateUserStatusRequestDto;
import com.example.bankcards.dto.response.FullUserResponseDto;
import com.example.bankcards.dto.response.UserResponseDto;
import com.example.bankcards.exception.dto.ErrorResponseDto;
import com.example.bankcards.service.UserService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bank-rest/user")
@Tag(name = "Управление пользователями", description = "Предоставляет эндпоинты для управления пользователями")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создать нового пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для создания пользователя не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add-new-user")
    public UserResponseDto createUser(@RequestBody @Valid RegistrationUserRequestDto dto) {
        return userService.createUser(dto);
    }

    @Operation(summary = "Получить всех пользователей")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователи успешно получены"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для получения пользователей не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/get-all-users")
    public List<FullUserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Удалить пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователи успешно удален"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для удаления пользователя не найден",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete-user")
    public UserResponseDto deleteUser(@RequestBody @Valid Long userId) {
        return userService.deleteUser(userId);
    }

    @Operation(summary = "Установить статус пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Статус пользователя успешно установлен"),
        @ApiResponse(responseCode = "401", description = "Неуспешная валидация токена",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "403", description = "Отказано в доступе, неверная роль пользователя",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "404", description = "Ресурс для установки статуса пользователя не найде",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}),
        @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера",
            content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))})
    })
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update-user")
    public UserResponseDto updateUserStatus(@RequestBody @Valid UpdateUserStatusRequestDto dto) {
        return userService.updateUserStatus(dto);
    }

}

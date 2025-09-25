package com.example.bankcards.controller;

import com.example.bankcards.dto.AppError;
import com.example.bankcards.dto.request.AuthenticateRequestDto;
import com.example.bankcards.dto.response.AuthenticateResponseDto;
import com.example.bankcards.service.AuthService;
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
public class AuthController {

    private final AuthService authService;


    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticateRequestDto dto) {

        AuthenticateResponseDto responseDto = null;

        try {
            responseDto= authService.authenticate(dto);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "Неверный логин или пароль"), HttpStatus.UNAUTHORIZED
            );
        }

        return ResponseEntity.ok(responseDto);
    }

}


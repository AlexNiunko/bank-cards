package com.example.bankcards.service;

import com.example.bankcards.dto.request.AuthenticateRequestDto;
import com.example.bankcards.dto.response.AuthenticateResponseDto;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthService {

    AuthenticateResponseDto authenticate(AuthenticateRequestDto dto) throws BadCredentialsException;

}

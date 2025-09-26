package com.example.bankcards.security.authentication.impl;

import com.example.bankcards.security.authentication.AuthService;
import com.example.bankcards.security.JwtTokenUtils;
import com.example.bankcards.security.authentication.dto.GenerateTokenDto;
import com.example.bankcards.dto.request.AuthenticateRequestDto;
import com.example.bankcards.dto.response.AuthenticateResponseDto;
import com.example.bankcards.security.authentication.dto.UserPrincipals;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService service;

    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticateResponseDto authenticate(AuthenticateRequestDto dto) throws BadCredentialsException {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.login(), dto.password())
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationCredentialsNotFoundException("Неверный логин");
        }

        UserDetails userDetails = service.loadUserByUsername(dto.login());

        UserPrincipals userPrincipals = (UserPrincipals) userDetails;

        GenerateTokenDto generateTokenDto = GenerateTokenDto.builder()
            .userId(userPrincipals.id())
            .login(userPrincipals.login())
            .roles(userPrincipals.roles())
            .build();

        String token = jwtTokenUtils.generateToken(generateTokenDto);

        return new AuthenticateResponseDto(token);
    }

}

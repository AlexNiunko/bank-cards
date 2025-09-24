package com.example.bankcards.service.impl;

import com.example.bankcards.config.JwtTokenUtils;
import com.example.bankcards.dto.GenerateTokenDto;
import com.example.bankcards.dto.request.AuthenticateRequestDto;
import com.example.bankcards.dto.response.AuthenticateResponseDto;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.AuthService;
import com.example.bankcards.dto.UserPrincipals;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService service;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public AuthenticateResponseDto authenticate(AuthenticateRequestDto dto) throws BadCredentialsException {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.login(), dto.password())
        );

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

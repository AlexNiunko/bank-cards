package com.example.bankcards.security.authentication.impl;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.Users;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.authentication.UserJwtService;
import com.example.bankcards.security.authentication.dto.UserPrincipals;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserJwtServiceImpl implements UserJwtService {

    private final UserRepository userRepository;

    @Override
    public UserPrincipals findByUserId(Long id) {

        Users user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(
            String.format("Пользователь с идентификатором: '%s' не найден", id)
        ));

        return UserPrincipals.builder()
            .id(user.getId())
            .roles(user.getRoles().stream().map(Role::getName).toList())
            .login(user.getLogin())
            .password(user.getPassword())
            .status(user.getStatus())
            .build();

    }
}

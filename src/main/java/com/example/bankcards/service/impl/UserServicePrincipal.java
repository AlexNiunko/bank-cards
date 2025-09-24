package com.example.bankcards.service.impl;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.Users;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.dto.UserPrincipals;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServicePrincipal implements UserDetailsService {

    private final UserRepository userRepository;

    public Optional<Users> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Users user = findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
            String.format("Пользователь '%s' не найден", login)
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

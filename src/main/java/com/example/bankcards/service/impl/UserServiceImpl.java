package com.example.bankcards.service.impl;

import static com.example.bankcards.util.encryption.ExceptionMessage.ROLE_NOT_EXIST;
import static com.example.bankcards.util.encryption.ExceptionMessage.USER_NOT_EXIST_BY_LOGIN;
import static com.example.bankcards.util.encryption.ExceptionMessage.USER_NOT_FOUND_BY_ID;

import com.example.bankcards.dto.request.RegistrationUserRequestDto;
import com.example.bankcards.dto.request.UpdateUserStatusRequestDto;
import com.example.bankcards.dto.response.FullUserResponseDto;
import com.example.bankcards.dto.response.UserResponseDto;
import com.example.bankcards.entity.ProfileStatus;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.UserInfo;
import com.example.bankcards.entity.Users;
import com.example.bankcards.exception.BusinessException;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER = "USER";

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponseDto createUser(RegistrationUserRequestDto dto) {
        String login = dto.login();

        userRepository.findByLogin(login)
            .ifPresent(u -> {
                throw new BusinessException(String.format(USER_NOT_EXIST_BY_LOGIN, login));
            });

        Role role = roleRepository.findByName(USER)
            .orElseThrow(
                () -> new BusinessException(String.format(ROLE_NOT_EXIST, USER))
            );

        var user = getUser(dto, role);

        Users savedUser = userRepository.save(user);

        return getResponseDto(savedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FullUserResponseDto> getAllUsers() {

        List<FullUserResponseDto> result = new ArrayList<>();

        userRepository.findAll().forEach(item -> result.add(getFullUserResponseDto(item)));

        return result;
    }

    @Transactional
    @Override
    public UserResponseDto deleteUser(Long userId) {
        var user = getUsers(userId);

        userRepository.delete(user);

        return getResponseDto(user);
    }

    @Transactional
    @Override
    public UserResponseDto updateUserStatus(UpdateUserStatusRequestDto dto) {
        var userId = dto.userId();

        var user = getUsers(userId);

        user.setStatus(ProfileStatus.valueOf(dto.status()));

        return getResponseDto(user);
    }

    private UserResponseDto getResponseDto(Users savedUser) {
        return com.example.bankcards.dto.response.UserResponseDto.builder()
            .userId(savedUser.getId())
            .firstname(savedUser.getUserInfo().getFirstname())
            .lastName(savedUser.getUserInfo().getLastname())
            .build();
    }

    private Users getUser(RegistrationUserRequestDto dto, Role role) {
        return Users.builder()
            .userInfo(
                UserInfo.builder()
                    .firstname(dto.firstname())
                    .lastname(dto.lastname())
                    .phoneNumber(dto.phoneNumber())
                    .birthDate(dto.birthDate())
                    .build())
            .roles(List.of(role))
            .status(ProfileStatus.ACTIVE)
            .login(dto.login())
            .password(passwordEncoder.encode(dto.password()))
            .build();
    }

    private FullUserResponseDto getFullUserResponseDto(Users item) {

        return FullUserResponseDto.builder()
            .userId(item.getId())
            .login(item.getLogin())
            .roles(
                item.getRoles().stream()
                    .map(Role::getName)
                    .toList())
            .birthDate(item.getUserInfo().getBirthDate())
            .firstname(item.getUserInfo().getFirstname())
            .lastname(item.getUserInfo().getLastname())
            .phoneNumber(item.getUserInfo().getPhoneNumber())
            .status(item.getStatus())
            .build();
    }

    private Users getUsers(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new BusinessException(String.format(USER_NOT_FOUND_BY_ID, userId))
        );

    }
}

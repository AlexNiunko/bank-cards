package com.example.bankcards.service;

import com.example.bankcards.dto.response.FullUserResponseDto;
import com.example.bankcards.dto.request.RegistrationUserRequestDto;
import com.example.bankcards.dto.request.UpdateUserStatusRequestDto;
import java.util.List;

public interface UserService {

    com.example.bankcards.dto.response.UserResponseDto createUser(RegistrationUserRequestDto dto);

    List<FullUserResponseDto> getAllUsers();

    com.example.bankcards.dto.response.UserResponseDto deleteUser(Long userId);

    com.example.bankcards.dto.response.UserResponseDto updateUserStatus(UpdateUserStatusRequestDto dto);

}

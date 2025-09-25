package com.example.bankcards.controller;

import com.example.bankcards.dto.response.FullUserResponseDto;
import com.example.bankcards.dto.request.RegistrationUserRequestDto;
import com.example.bankcards.dto.request.UpdateUserStatusRequestDto;
import com.example.bankcards.dto.response.UserResponseDto;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import java.util.ArrayList;
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
public class UserController {

    private final UserService userService;

    @PostMapping("/add-new-user")
    public UserResponseDto createUser(@RequestBody @Valid RegistrationUserRequestDto dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/get-all-users")
    public List<FullUserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete-user")
    public UserResponseDto deleteUser(@RequestBody @Valid Long userId) {
        return userService.deleteUser(userId);
    }

    @PatchMapping("/update-user")
    public UserResponseDto updateUserStatus(@RequestBody @Valid UpdateUserStatusRequestDto dto) {
        return userService.updateUserStatus(dto);
    }

}

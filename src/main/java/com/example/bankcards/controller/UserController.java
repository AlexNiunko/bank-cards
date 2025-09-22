package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.request.LoginRequestDto;
import com.example.bankcards.dto.request.RegistrationUserRequestDto;
import com.example.bankcards.dto.request.DeleteUserRequestDto;
import com.example.bankcards.dto.request.UpdateUserRequestDto;
import com.example.bankcards.dto.response.LoginResponseDto;
import com.example.bankcards.dto.response.RegistrationUserResponseDto;
import com.example.bankcards.dto.response.DeleteUserResponseDto;
import com.example.bankcards.dto.response.UpdateUserResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank-rest/user")
public class UserController {

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto){
        LoginResponseDto response=new LoginResponseDto();
        return response;
    }

    @PostMapping("/registration")
    public RegistrationUserResponseDto createUser(@RequestBody RegistrationUserRequestDto dto) {
        RegistrationUserResponseDto response = new RegistrationUserResponseDto();
        return response;
    }

    @GetMapping("/get-all-users")
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        return userDtoList;
    }

    @DeleteMapping("/delete-user")
    public DeleteUserResponseDto deleteUser(DeleteUserRequestDto dto) {
        DeleteUserResponseDto response = new DeleteUserResponseDto();
        return response;
    }

    @PatchMapping("/update-user")
    public UpdateUserResponseDto updateUser(UpdateUserRequestDto dto) {
        UpdateUserResponseDto response = new UpdateUserResponseDto();
        return response;
    }


}

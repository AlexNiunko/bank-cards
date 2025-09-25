package com.example.bankcards.controller;

import com.example.bankcards.dto.request.TransferRequestDto;
import com.example.bankcards.dto.response.BalanceResponseDto;
import com.example.bankcards.dto.response.CardResponseBlockDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.dto.response.TransferResponseDto;
import com.example.bankcards.service.UserOperationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank-rest/user-operation")
@RequiredArgsConstructor
public class UserOperationController {

    private final UserOperationService userOperationService;

    @GetMapping("/get-user-cards")
    public List<FullCardResponseDto>getUserCard(@RequestBody Long userId){
        return userOperationService.getUserCards(userId);
    }

    @PostMapping("/request-block-card")
    public CardResponseBlockDto requestBlockCard(@RequestBody Long cardId){
        return userOperationService.requestBlockCard(cardId);
    }

    @PostMapping("/transfer")
    public TransferResponseDto transfer(@RequestBody @Valid TransferRequestDto dto){
        return userOperationService.transfer(dto);
    }

    @GetMapping("/balance")
    public BalanceResponseDto getBalance(@RequestBody Long cardId){
        return userOperationService.getBalance(cardId);
    }

}

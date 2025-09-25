package com.example.bankcards.service;

import com.example.bankcards.dto.request.TransferRequestDto;
import com.example.bankcards.dto.response.BalanceResponseDto;
import com.example.bankcards.dto.response.CardResponseBlockDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.dto.response.TransferResponseDto;
import java.util.List;

public interface UserOperationService {

    List<FullCardResponseDto> getUserCards(Long userId);

    CardResponseBlockDto requestBlockCard(Long cardId);

    TransferResponseDto transfer(TransferRequestDto dto);

    BalanceResponseDto getBalance(Long cardId);
}

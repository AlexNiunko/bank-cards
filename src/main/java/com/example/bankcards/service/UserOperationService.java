package com.example.bankcards.service;

import com.example.bankcards.dto.request.BalanceRequestDto;
import com.example.bankcards.dto.request.RequestCardBlockDto;
import com.example.bankcards.dto.request.TransferRequestDto;
import com.example.bankcards.dto.response.BalanceResponseDto;
import com.example.bankcards.dto.response.CardResponseBlockDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.dto.response.TransferResponseDto;
import java.util.List;

public interface UserOperationService {

    List<FullCardResponseDto> getUserCards(Long userId);

    CardResponseBlockDto requestBlockCard(RequestCardBlockDto dto);

    TransferResponseDto transfer(TransferRequestDto dto);

    BalanceResponseDto getBalance(BalanceRequestDto dto);


}

package com.example.bankcards.service;

import com.example.bankcards.dto.request.CreateCardRequestDto;
import com.example.bankcards.dto.response.CardResponseDto;
import com.example.bankcards.dto.response.CreateCardResponseDto;

public interface CardService {

    CreateCardResponseDto createCard(CreateCardRequestDto dto);

    CardResponseDto blockCard(Long cardId);

    CardResponseDto deleteCard(Long cardId);

    CardResponseDto activateCard(Long cardId);

}

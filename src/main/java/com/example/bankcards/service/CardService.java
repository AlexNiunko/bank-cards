package com.example.bankcards.service;

import com.example.bankcards.dto.request.CreateCardRequestDto;
import com.example.bankcards.dto.request.PageableCardRequest;
import com.example.bankcards.dto.request.UpdateCardStatusRequestDto;
import com.example.bankcards.dto.response.CardResponseDto;
import com.example.bankcards.dto.response.CreateCardResponseDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {

    CreateCardResponseDto createCard(CreateCardRequestDto dto);

    CardResponseDto deleteCard(Long cardId);

    CardResponseDto updateCardStatus(UpdateCardStatusRequestDto dto);

    List<FullCardResponseDto> getAllCards();

    List<FullCardResponseDto> getAllCardsUsingPageable(PageableCardRequest dto);
}

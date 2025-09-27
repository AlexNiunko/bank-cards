package com.example.bankcards.repository;

import com.example.bankcards.dto.request.FilterCardRequestDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import java.util.List;

public interface CardCriteriaRepository {

    List<FullCardResponseDto> getCardsByFilter(FilterCardRequestDto dto);

}

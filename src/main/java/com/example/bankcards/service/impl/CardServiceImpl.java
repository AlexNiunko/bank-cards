package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.CreateCardRequestDto;
import com.example.bankcards.dto.request.UpdateCardStatusRequestDto;
import com.example.bankcards.dto.response.CardResponseDto;
import com.example.bankcards.dto.response.CreateCardResponseDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.PaymentSystem;
import com.example.bankcards.entity.Users;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public CreateCardResponseDto createCard(CreateCardRequestDto dto) {
        String cardNumber = dto.cardNumber();
        List<Card> cardList = cardRepository.findByCardNumber(cardNumber);
        if (!cardList.isEmpty()) {
            throw new RuntimeException(String.format("Карта с номером - %s уже существует", cardNumber));
        }

        Long userId = dto.userId();

        Users user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException(String.format("Пользователя с id: %s не существует", userId)));

        Card card = getCard(dto, user);

        Card savedCard = cardRepository.save(card);

        return getCardResponseDto(savedCard, user);
    }


    @Transactional
    @Override
    public CardResponseDto deleteCard(Long cardId) {

        var card = getCard(cardId);

        cardRepository.delete(card);

        return getCardResponseDto(cardId, card);
    }

    @Transactional
    @Override
    public CardResponseDto updateCardStatus(UpdateCardStatusRequestDto dto) {
        var cardId = dto.cardId();

        var card = getCard(cardId);

        card.setStatus(dto.status());

        cardRepository.save(card);

        return getCardResponseDto(cardId, card);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FullCardResponseDto> getAllCards() {
        List<FullCardResponseDto> result = new ArrayList<>();

        cardRepository.findAll().forEach(item -> {
                result.add(getFullCardResponseDto(item));
            }
        );

        return result;
    }

    private FullCardResponseDto getFullCardResponseDto(Card item) {
        return FullCardResponseDto.builder()
            .cardId(item.getId())
            .cardNumber(item.getCardNumber())
            .balance(item.getBalance())
            .paymentSystem(item.getSystem())
            .status(item.getStatus())
            .expirationTime(item.getExpirationTime())
            .userId(item.getUser().getId())
            .currency(item.getCurrency())
            .build();
    }

    private CreateCardResponseDto getCardResponseDto(Card savedCard, Users user) {
        return CreateCardResponseDto.builder()
            .cardId(savedCard.getId())
            .firstName(user.getUserInfo().getFirstname())
            .lastName(user.getUserInfo().getLastname())
            .build();
    }

    private Card getCard(CreateCardRequestDto dto, Users user) {
        return Card.builder()
            .cardNumber(dto.cardNumber())
            .currency(dto.currency())
            .expirationTime(dto.expirationTime())
            .status(CardStatus.BLOCKED)
            .system(PaymentSystem.valueOf(dto.paymentSystem()))
            .user(user)
            .build();

    }

    private static CardResponseDto getCardResponseDto(Long cardId, Card card) {
        return CardResponseDto.builder()
            .cardId(cardId)
            .firstname(card.getUser().getUserInfo().getFirstname())
            .lastname(card.getUser().getUserInfo().getLastname())
            .build();
    }

    private Card getCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new RuntimeException(String.format("Карты с идентификатором: %s", cardId))
        );
    }
}

package com.example.bankcards.service.impl;

import static com.example.bankcards.util.encryption.ExceptionMessage.CARD_NOT_FOUND_BY_ID;
import static com.example.bankcards.util.encryption.ExceptionMessage.DUPLICATE_CARD;
import static com.example.bankcards.util.encryption.ExceptionMessage.USER_NOT_EXIST_BY_ID;

import com.example.bankcards.dto.request.CreateCardRequestDto;
import com.example.bankcards.dto.request.PageableCardRequest;
import com.example.bankcards.dto.request.UpdateCardStatusRequestDto;
import com.example.bankcards.dto.response.CardResponseDto;
import com.example.bankcards.dto.response.CreateCardResponseDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.Users;
import com.example.bankcards.exception.BusinessException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.encryption.EncryptionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    private final EncryptionService encryptionService;

    @Transactional
    @Override
    public CreateCardResponseDto createCard(CreateCardRequestDto dto) {
        String cardNumber = dto.cardNumber();
        List<Card> cardList = cardRepository.findByCardNumber(cardNumber);
        if (!cardList.isEmpty()) {
            throw new BusinessException(String.format(DUPLICATE_CARD, cardNumber));
        }

        Long userId = dto.userId();

        Users user = userRepository.findById(userId).orElseThrow(
            () -> new BusinessException(String.format(USER_NOT_EXIST_BY_ID, userId)));

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

        card.setStatus(CardStatus.valueOf(dto.status()));

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

    @Transactional
    @Override
    public List<FullCardResponseDto> getAllCardsUsingPageable(PageableCardRequest dto) {
        var pageSize = dto.pageSize();
        var pageNumber = dto.pageNumber();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<FullCardResponseDto> result = new ArrayList<>();
        cardRepository.findAll(pageable).forEach(
            item -> {
                result.add(getFullCardResponseDto(item));
            }
        );
        return result;
    }

    private FullCardResponseDto getFullCardResponseDto(Card item) {
        return FullCardResponseDto.builder()
            .cardId(item.getId())
            .cardNumber(encryptionService.decrypt(item.getCardNumber()))
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
            .cardNumber(encryptionService.encrypt(dto.cardNumber()))
            .currency(dto.currency())
            .expirationTime(dto.expirationTime())
            .status(CardStatus.BLOCKED)
            .system(dto.paymentSystem())
            .user(user)
            .balance(BigDecimal.ZERO)
            .build();

    }

    private Card getCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new BusinessException(String.format(CARD_NOT_FOUND_BY_ID, cardId))
        );
    }

    private CardResponseDto getCardResponseDto(Long cardId, Card card) {
        return CardResponseDto.builder()
            .cardId(cardId)
            .firstname(card.getUser().getUserInfo().getFirstname())
            .lastname(card.getUser().getUserInfo().getLastname())
            .build();
    }
}

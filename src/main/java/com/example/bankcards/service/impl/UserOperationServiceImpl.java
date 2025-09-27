package com.example.bankcards.service.impl;

import static com.example.bankcards.util.encryption.ExceptionMessage.CARDS_DOES_NOT_OWN_USER;
import static com.example.bankcards.util.encryption.ExceptionMessage.CARD_BLOCKED;
import static com.example.bankcards.util.encryption.ExceptionMessage.CARD_DOES_NOT_OWN_USER;
import static com.example.bankcards.util.encryption.ExceptionMessage.CARD_NOT_FOUND_BY_ID;
import static com.example.bankcards.util.encryption.ExceptionMessage.INSUFFICIENT_FUNDS_ON_THE_CARD;
import static com.example.bankcards.util.encryption.ExceptionMessage.USER_NOT_FOUND_BY_ID;

import com.example.bankcards.dto.request.BalanceRequestDto;
import com.example.bankcards.dto.request.RequestCardBlockDto;
import com.example.bankcards.dto.request.TransferRequestDto;
import com.example.bankcards.dto.response.BalanceResponseDto;
import com.example.bankcards.dto.response.CardResponseBlockDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.dto.response.TransferResponseDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.RequestCardBlock;
import com.example.bankcards.entity.Users;
import com.example.bankcards.exception.BusinessException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.RequestCardBlockRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserOperationService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserOperationServiceImpl implements UserOperationService {

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    private final RequestCardBlockRepository requestCardBlockRepository;

    @Transactional
    @Override
    public List<FullCardResponseDto> getUserCards(Long userId) {
        List<FullCardResponseDto> result = new ArrayList<>();
        Users user = userRepository.findById(userId).orElseThrow(
            () -> new BusinessException(String.format(USER_NOT_FOUND_BY_ID, userId))
        );
        cardRepository.getAllByUser(user).forEach(item -> result.add(getFullCardResponseDto(item)));
        return result;
    }

    @Transactional
    @Override
    public CardResponseBlockDto requestBlockCard(RequestCardBlockDto dto) {
        var cardId = dto.cardId();
        var userId = dto.userId();

        var card = getCard(cardId);

        checkCardOwner(card, userId);

        RequestCardBlock request = RequestCardBlock.builder()
            .card(card)
            .build();

        RequestCardBlock requestCardBlock = requestCardBlockRepository.save(request);

        return CardResponseBlockDto.builder()
            .cardId(cardId)
            .requestId(requestCardBlock.getId())
            .build();
    }

    @Transactional
    @Override
    public TransferResponseDto transfer(TransferRequestDto dto) {
        Long fromCardId = dto.fromCardId();
        Long toCardId = dto.toCardId();
        Long userId = dto.userId();
        BigDecimal amount = dto.amount();

        Card fromCard = getCard(fromCardId);
        Card toCard = getCard(toCardId);

        if (!fromCard.getUser().getId().equals(userId) || !toCard.getUser().getId().equals(userId)) {
            throw new BusinessException(
                String.format(CARDS_DOES_NOT_OWN_USER, userId));
        }

        checkCardStatus(fromCard, fromCardId);
        checkCardStatus(toCard, toCardId);
        checkCardBalance(fromCard, amount, fromCardId);

        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));

        Card savedFrom = cardRepository.save(fromCard);
        Card savedTo = cardRepository.save(toCard);

        return getTransferResponseDto(savedFrom, savedTo, amount);
    }

    @Transactional(readOnly = true)
    @Override
    public BalanceResponseDto getBalance(BalanceRequestDto dto) {
        var cardId = dto.cardId();
        var userId = dto.userId();

        var card = getCard(cardId);
        checkCardOwner(card, userId);

        return getBalanceResponseDto(cardId, card);
    }

    private void checkCardOwner(Card card, Long userId) {
        var cardOwnerId = card.getUser().getId();
        if (!userId.equals(cardOwnerId)) {
            throw new BusinessException(
                String.format(CARD_DOES_NOT_OWN_USER, card.getId(), cardOwnerId));
        }
    }

    private BalanceResponseDto getBalanceResponseDto(Long cardId, Card card) {
        return BalanceResponseDto.builder()
            .cardId(cardId)
            .amount(card.getBalance())
            .build();
    }

    private Card getCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new BusinessException(String.format(CARD_NOT_FOUND_BY_ID, cardId)));

    }

    private FullCardResponseDto getFullCardResponseDto(Card item) {
        return FullCardResponseDto.builder()
            .cardNumber(item.getCardNumber())
            .expirationTime(item.getExpirationTime())
            .status(item.getStatus())
            .paymentSystem(item.getSystem())
            .currency(item.getCurrency())
            .cardId(item.getId())
            .balance(item.getBalance())
            .userId(item.getUser().getId())
            .build();
    }

    private TransferResponseDto getTransferResponseDto(Card savedFrom, Card savedTo, BigDecimal amount) {
        return TransferResponseDto.builder()
            .fromBalance(savedFrom.getBalance())
            .toBalance(savedTo.getBalance())
            .amount(amount)
            .build();
    }

    private void checkCardBalance(Card fromCard, BigDecimal amount, Long fromCardId) {
        if (fromCard.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(
                String.format(INSUFFICIENT_FUNDS_ON_THE_CARD, fromCardId));
        }
    }

    private void checkCardStatus(Card fromCard, Long fromCardId) {
        if (fromCard.getStatus().equals(CardStatus.BLOCKED)) {
            throw new BusinessException(String.format(CARD_BLOCKED, fromCardId));
        }
    }

}

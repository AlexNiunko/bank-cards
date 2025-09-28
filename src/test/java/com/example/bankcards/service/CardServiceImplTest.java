package com.example.bankcards.service;

import static com.example.bankcards.util.encryption.ExceptionMessage.CARD_NOT_FOUND_BY_ID;
import static com.example.bankcards.util.encryption.ExceptionMessage.DUPLICATE_CARD;
import static com.example.bankcards.util.encryption.ExceptionMessage.USER_NOT_EXIST_BY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.example.bankcards.dto.request.CreateCardRequestDto;
import com.example.bankcards.dto.request.PageableCardRequest;
import com.example.bankcards.dto.request.UpdateCardStatusRequestDto;
import com.example.bankcards.dto.response.CreateCardResponseDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.CurrencyType;
import com.example.bankcards.entity.PaymentSystem;
import com.example.bankcards.entity.UserInfo;
import com.example.bankcards.entity.Users;
import com.example.bankcards.exception.BusinessException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.impl.CardServiceImpl;
import com.example.bankcards.util.encryption.EncryptionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void createCard_WithValidData_ShouldCreateCardSuccessfully() {

        CreateCardRequestDto requestDto = new CreateCardRequestDto(
            1L,
            PaymentSystem.VISA,
            LocalDateTime.now().plusYears(3),
            CurrencyType.USD,
            "1234567812345678"
        );

        Users user = createTestUser();
        Card savedCard = createTestCard();
        String encryptedCardNumber = "encrypted-1234567812345678";

        when(cardRepository.findByCardNumber(any())).thenReturn(List.of());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(encryptionService.encrypt("1234567812345678")).thenReturn(encryptedCardNumber);
        when(cardRepository.save(any(Card.class))).thenReturn(savedCard);

        CreateCardResponseDto result = cardService.createCard(requestDto);
        Long cardId = result.cardId();

        assertNotNull(result);
        assertEquals(1L, cardId);
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());

        verify(cardRepository).findByCardNumber(any());
        verify(userRepository).findById(1L);
        verify(encryptionService).encrypt("1234567812345678");
        verify(cardRepository).save(any(Card.class));
    }


    @Test
    void createCard_WithDuplicateCardNumber_ShouldThrowBusinessException() {
        // Given
        CreateCardRequestDto requestDto = new CreateCardRequestDto(
            1L,
            PaymentSystem.VISA,
            LocalDateTime.now().plusYears(3),
            CurrencyType.USD,
            "1234567812345678"
        );

        Card existingCard = createTestCard();

        when(cardRepository.findByCardNumber(any())).thenReturn(List.of(existingCard));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
            () -> cardService.createCard(requestDto));

        assertTrue(exception.getMessage().contains(String.format(DUPLICATE_CARD, "1234567812345678")));
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void createCard_WithNonExistentUser_ShouldThrowBusinessException() {
        // Given
        CreateCardRequestDto requestDto = new CreateCardRequestDto(
            1L,
            PaymentSystem.VISA,
            LocalDateTime.now().plusYears(3),
            CurrencyType.USD,
            "1234567812345678"
        );

        when(cardRepository.findByCardNumber(any())).thenReturn(List.of());
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
            () -> cardService.createCard(requestDto));

        assertTrue(exception.getMessage().contains(String.format(USER_NOT_EXIST_BY_ID, 1L)));
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void updateCardStatus_WithValidData_ShouldUpdateSuccessfully() {
        // Given
        Long cardId = 1L;
        UpdateCardStatusRequestDto requestDto =
            new UpdateCardStatusRequestDto(cardId, "ACTIVE");

        Card card = createTestCard();
        card.setStatus(CardStatus.BLOCKED);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(cardRepository.save(card)).thenReturn(card);

        // When
        var result = cardService.updateCardStatus(requestDto);

        // Then
        assertNotNull(result);
        assertEquals(CardStatus.ACTIVE, card.getStatus());
        assertEquals(cardId, result.cardId());

        verify(cardRepository).findById(cardId);
        verify(cardRepository).save(card);
    }

    @Test
    void updateCardStatus_WithInvalidStatus_ShouldThrowException() {
        // Given
        Long cardId = 1L;
        UpdateCardStatusRequestDto requestDto =
            new UpdateCardStatusRequestDto(cardId, "INVALID_STATUS");

        Card card = createTestCard();

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        // When & Then
        assertThrows(IllegalArgumentException.class,
            () -> cardService.updateCardStatus(requestDto));
    }

    @Test
    void getAllCards_ShouldReturnAllCards() {
        // Given
        Card card1 = createTestCard();
        Card card2 = createTestCard();
        card2.setId(2L);

        String decryptedCardNumber = "1234567812345678";

        when(cardRepository.findAll()).thenReturn(List.of(card1, card2));
        when(encryptionService.decrypt(any())).thenReturn(decryptedCardNumber);

        // When
        List<FullCardResponseDto> result = cardService.getAllCards();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        FullCardResponseDto firstCard = result.get(0);
        assertEquals(1L, firstCard.cardId());
        assertEquals(decryptedCardNumber, firstCard.cardNumber());
        assertEquals(BigDecimal.valueOf(1000), firstCard.balance());

        verify(cardRepository).findAll();
        verify(encryptionService, times(2)).decrypt(any());
    }

    @Test
    void getAllCardsUsingPageable_WithPageableRequest_ShouldReturnPagedCards() {
        // Given
        PageableCardRequest request = new PageableCardRequest(0, 10);
        Card card1 = createTestCard();
        Card card2 = createTestCard();
        card2.setId(2L);

        Page<Card> cardPage = new PageImpl<>(List.of(card1, card2));
        String decryptedCardNumber = "1234567812345678";

        when(cardRepository.findAll(any(Pageable.class))).thenReturn(cardPage);
        when(encryptionService.decrypt(any())).thenReturn(decryptedCardNumber);

        // When
        List<FullCardResponseDto> result = cardService.getAllCardsUsingPageable(request);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(cardRepository).findAll(PageRequest.of(0, 10));
        verify(encryptionService, times(2)).decrypt(any());
    }

    @Test
    void getAllCardsUsingPageable_WithEmptyPage_ShouldReturnEmptyList() {
        // Given
        PageableCardRequest request = new PageableCardRequest(0, 10);
        Page<Card> emptyPage = new PageImpl<>(List.of());

        when(cardRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        // When
        List<FullCardResponseDto> result = cardService.getAllCardsUsingPageable(request);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(cardRepository).findAll(PageRequest.of(0, 10));
        verify(encryptionService, never()).decrypt(any());
    }

    @Test
    void deleteCard_WithExistingCard_ShouldDeleteSuccessfully() {
        // Given
        Long cardId = 1L;
        Card card = createTestCard();

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        doNothing().when(cardRepository).delete(card);

        // When
        var result = cardService.deleteCard(cardId);

        // Then
        assertNotNull(result);
        assertEquals(cardId, result.cardId());
        assertEquals("John", result.firstname());
        assertEquals("Doe", result.lastname());

        verify(cardRepository).findById(cardId);
        verify(cardRepository).delete(card);
    }

    @Test
    void deleteCard_WithNonExistentCard_ShouldThrowBusinessException() {
        // Given
        Long cardId = 999L;

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
            () -> cardService.deleteCard(cardId));

        assertTrue(exception.getMessage().contains(String.format(CARD_NOT_FOUND_BY_ID, cardId)));
        verify(cardRepository, never()).delete(any());
    }

    private Users createTestUser() {
        UserInfo userInfo = UserInfo.builder()
            .firstname("John")
            .lastname("Doe")
            .build();

        return Users.builder()
            .id(1L)
            .userInfo(userInfo)
            .build();
    }

    private Card createTestCard() {
        Users user = createTestUser();

        return Card.builder()
            .id(1L)
            .cardNumber("encrypted-card-number")
            .system(PaymentSystem.VISA)
            .currency(CurrencyType.USD)
            .expirationTime(LocalDateTime.now())
            .status(CardStatus.BLOCKED)
            .user(user)
            .balance(BigDecimal.valueOf(1000))
            .build();
    }

}

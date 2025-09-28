package com.example.bankcards.service;

import static com.example.bankcards.util.encryption.ExceptionMessage.CARDS_DOES_NOT_OWN_USER;
import static com.example.bankcards.util.encryption.ExceptionMessage.CARD_BLOCKED;
import static com.example.bankcards.util.encryption.ExceptionMessage.CARD_DOES_NOT_OWN_USER;
import static com.example.bankcards.util.encryption.ExceptionMessage.CARD_NOT_FOUND_BY_ID;
import static com.example.bankcards.util.encryption.ExceptionMessage.INSUFFICIENT_FUNDS_ON_THE_CARD;
import static com.example.bankcards.util.encryption.ExceptionMessage.USER_NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
import com.example.bankcards.service.impl.UserOperationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserOperationServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestCardBlockRepository requestCardBlockRepository;

    @InjectMocks
    private UserOperationServiceImpl userOperationService;

    private Users user;
    private Card activeCard1;
    private Card activeCard2;
    private Card blockedCard;
    private Long userId;
    private Long cardId1;
    private Long cardId2;

    @BeforeEach
    void setUp() {
        userId = 1L;
        cardId1 = 100L;
        cardId2 = 200L;

        user = new Users();
        user.setId(userId);

        activeCard1 = new Card();
        activeCard1.setId(cardId1);
        activeCard1.setBalance(new BigDecimal("1000.00"));
        activeCard1.setStatus(CardStatus.ACTIVE);
        activeCard1.setUser(user);

        activeCard2 = new Card();
        activeCard2.setId(cardId2);
        activeCard2.setBalance(new BigDecimal("500.00"));
        activeCard2.setStatus(CardStatus.ACTIVE);
        activeCard2.setUser(user);

        blockedCard = new Card();
        blockedCard.setId(300L);
        blockedCard.setBalance(new BigDecimal("200.00"));
        blockedCard.setStatus(CardStatus.BLOCKED);
        blockedCard.setUser(user);
    }

    @Test
    void getUserCards_WhenUserExists_ShouldReturnUserCards() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cardRepository.getAllByUser(user)).thenReturn(Arrays.asList(activeCard1, activeCard2));

        // Act
        List<FullCardResponseDto> result = userOperationService.getUserCards(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findById(userId);
        verify(cardRepository, times(1)).getAllByUser(user);
    }

    @Test
    void getUserCards_WhenUserNotFound_ShouldThrowException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userOperationService.getUserCards(userId));
        assertTrue(exception.getMessage().contains(String.format(USER_NOT_FOUND_BY_ID, userId)));
        verify(userRepository, times(1)).findById(userId);
        verify(cardRepository, never()).getAllByUser(any());
    }

    @Test
    void requestBlockCard_WhenValidRequest_ShouldReturnResponse() {
        // Arrange
        RequestCardBlockDto dto = new RequestCardBlockDto(userId, cardId1);
        RequestCardBlock savedRequest = RequestCardBlock.builder()
            .id(50L)
            .card(activeCard1)
            .build();

        when(cardRepository.findById(cardId1)).thenReturn(Optional.of(activeCard1));
        when(requestCardBlockRepository.save(any(RequestCardBlock.class))).thenReturn(savedRequest);

        // Act
        CardResponseBlockDto result = userOperationService.requestBlockCard(dto);

        // Assert
        assertNotNull(result);
        assertEquals(cardId1, result.cardId());
        assertEquals(50L, result.requestId());
        verify(cardRepository, times(1)).findById(cardId1);
        verify(requestCardBlockRepository, times(1)).save(any(RequestCardBlock.class));
    }

    @Test
    void requestBlockCard_WhenCardNotFound_ShouldThrowException() {
        // Arrange
        RequestCardBlockDto dto = new RequestCardBlockDto(userId, cardId1);
        when(cardRepository.findById(cardId1)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userOperationService.requestBlockCard(dto));
        assertTrue(exception.getMessage().contains(String.format(CARD_NOT_FOUND_BY_ID, cardId1)));
        verify(cardRepository, times(1)).findById(cardId1);
        verify(requestCardBlockRepository, never()).save(any());
    }

    @Test
    void requestBlockCard_WhenUserNotOwner_ShouldThrowException() {
        // Arrange
        Long otherUserId = 999L;
        RequestCardBlockDto dto = new RequestCardBlockDto(otherUserId,cardId1);
        when(cardRepository.findById(cardId1)).thenReturn(Optional.of(activeCard1));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userOperationService.requestBlockCard(dto));
        assertTrue(exception.getMessage().contains(String.format(CARD_DOES_NOT_OWN_USER, cardId1, activeCard1.getUser().getId())));
        verify(cardRepository, times(1)).findById(cardId1);
        verify(requestCardBlockRepository, never()).save(any());
    }

    @Test
    void transfer_WhenValidTransfer_ShouldCompleteSuccessfully() {
        // Arrange
        BigDecimal amount = new BigDecimal("100.00");
        TransferRequestDto dto = new TransferRequestDto(userId,cardId1, cardId2, amount);

        Card fromCardAfterTransfer = new Card();
        fromCardAfterTransfer.setId(cardId1);
        fromCardAfterTransfer.setBalance(new BigDecimal("900.00"));
        fromCardAfterTransfer.setUser(user);

        Card toCardAfterTransfer = new Card();
        toCardAfterTransfer.setId(cardId2);
        toCardAfterTransfer.setBalance(new BigDecimal("600.00"));
        toCardAfterTransfer.setUser(user);

        when(cardRepository.findById(cardId1)).thenReturn(Optional.of(activeCard1));
        when(cardRepository.findById(cardId2)).thenReturn(Optional.of(activeCard2));
        when(cardRepository.save(activeCard1)).thenReturn(fromCardAfterTransfer);
        when(cardRepository.save(activeCard2)).thenReturn(toCardAfterTransfer);

        // Act
        TransferResponseDto result = userOperationService.transfer(dto);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("900.00"), result.fromBalance());
        assertEquals(new BigDecimal("600.00"), result.toBalance());
        assertEquals(amount, result.amount());

        verify(cardRepository, times(1)).findById(cardId1);
        verify(cardRepository, times(1)).findById(cardId2);
        verify(cardRepository, times(1)).save(activeCard1);
        verify(cardRepository, times(1)).save(activeCard2);
    }

    @Test
    void transfer_WhenInsufficientFunds_ShouldThrowException() {
        // Arrange
        BigDecimal amount = new BigDecimal("1500.00");
        TransferRequestDto dto = new TransferRequestDto(userId,cardId1, cardId2, amount);

        when(cardRepository.findById(cardId1)).thenReturn(Optional.of(activeCard1));
        when(cardRepository.findById(cardId2)).thenReturn(Optional.of(activeCard2));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userOperationService.transfer(dto));
        assertTrue(exception.getMessage().contains(String.format(INSUFFICIENT_FUNDS_ON_THE_CARD, cardId1)));
        verify(cardRepository, times(1)).findById(cardId1);
        verify(cardRepository, times(1)).findById(cardId2);
        verify(cardRepository, never()).save(any());
    }

    @Test
    void transfer_WhenFromCardBlocked_ShouldThrowException() {
        // Arrange
        BigDecimal amount = new BigDecimal("100.00");
        TransferRequestDto dto = new TransferRequestDto(userId,blockedCard.getId(), cardId2, amount);

        when(cardRepository.findById(blockedCard.getId())).thenReturn(Optional.of(blockedCard));
        when(cardRepository.findById(cardId2)).thenReturn(Optional.of(activeCard2));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userOperationService.transfer(dto));
        assertTrue(exception.getMessage().contains(String.format(CARD_BLOCKED, blockedCard.getId())));
        verify(cardRepository, times(1)).findById(blockedCard.getId());
        verify(cardRepository, times(1)).findById(cardId2);
        verify(cardRepository, never()).save(any());
    }

    @Test
    void transfer_WhenUserNotOwnerOfCards_ShouldThrowException() {
        // Arrange
        Long otherUserId = 999L;
        BigDecimal amount = new BigDecimal("100.00");
        TransferRequestDto dto = new TransferRequestDto(otherUserId,cardId1, cardId2,amount);

        when(cardRepository.findById(cardId1)).thenReturn(Optional.of(activeCard1));
        when(cardRepository.findById(cardId2)).thenReturn(Optional.of(activeCard2));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userOperationService.transfer(dto));
        assertTrue(exception.getMessage().contains(String.format(CARDS_DOES_NOT_OWN_USER, otherUserId)));
        verify(cardRepository, times(1)).findById(cardId1);
        verify(cardRepository, times(1)).findById(cardId2);
        verify(cardRepository, never()).save(any());
    }

    @Test
    void getBalance_WhenValidRequest_ShouldReturnBalance() {
        // Arrange
        BalanceRequestDto dto = new BalanceRequestDto(userId,cardId1);
        when(cardRepository.findById(cardId1)).thenReturn(Optional.of(activeCard1));

        // Act
        BalanceResponseDto result = userOperationService.getBalance(dto);

        // Assert
        assertNotNull(result);
        assertEquals(cardId1, result.cardId());
        assertEquals(new BigDecimal("1000.00"), result.amount());
        verify(cardRepository, times(1)).findById(cardId1);
    }

    @Test
    void getBalance_WhenCardNotFound_ShouldThrowException() {
        // Arrange
        BalanceRequestDto dto = new BalanceRequestDto(userId,cardId1);
        when(cardRepository.findById(cardId1)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userOperationService.getBalance(dto));
        assertTrue(exception.getMessage().contains(String.format(CARD_NOT_FOUND_BY_ID, cardId1)));
        verify(cardRepository, times(1)).findById(cardId1);
    }

    @Test
    void getBalance_WhenUserNotOwner_ShouldThrowException() {
        // Arrange
        Long otherUserId = 999L;
        BalanceRequestDto dto = new BalanceRequestDto(otherUserId,cardId1);
        when(cardRepository.findById(cardId1)).thenReturn(Optional.of(activeCard1));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userOperationService.getBalance(dto));
        assertTrue(exception.getMessage().contains(String.format(CARD_DOES_NOT_OWN_USER, cardId1, activeCard1.getUser().getId())));
        verify(cardRepository, times(1)).findById(cardId1);
    }

}

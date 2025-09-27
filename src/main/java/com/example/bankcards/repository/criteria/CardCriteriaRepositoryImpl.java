package com.example.bankcards.repository.criteria;

import com.example.bankcards.dto.request.FilterCardRequestDto;
import com.example.bankcards.dto.response.FullCardResponseDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.CurrencyType;
import com.example.bankcards.entity.PaymentSystem;
import com.example.bankcards.repository.CardCriteriaRepository;
import com.example.bankcards.util.encryption.EncryptionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardCriteriaRepositoryImpl implements CardCriteriaRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final EncryptionService encryptionService;

    public List<FullCardResponseDto> getCardsByFilter(FilterCardRequestDto dto) {

        List<FullCardResponseDto> result = new ArrayList<>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Card> query = cb.createQuery(Card.class);
        Root<Card> root = query.from(Card.class);

        var predicates = getPredicates(cb, root, dto);

        query.select(root).where(predicates.toArray(Predicate[]::new));
        fillResultList(query, result);

        return result;
    }

    private void fillResultList(CriteriaQuery<Card> query, List<FullCardResponseDto> result) {
        entityManager.createQuery(query)
            .getResultList()
            .forEach(item ->
                result.add(
                    FullCardResponseDto.builder()
                        .cardNumber(encryptionService.decrypt(item.getCardNumber()))
                        .userId(item.getUser().getId())
                        .cardId(item.getId())
                        .expirationTime(item.getExpirationTime())
                        .status(item.getStatus())
                        .currency(item.getCurrency())
                        .paymentSystem(item.getSystem())
                        .balance(item.getBalance())
                        .build()
                )
            );
    }

    private List<Predicate> getPredicates(CriteriaBuilder cb, Root<Card> root, FilterCardRequestDto dto) {
        BigDecimal maxBalance = dto.minBalance();
        BigDecimal minBalance = dto.maxBalance();
        String cardStatus = dto.cardStatus();
        String paymentSystem = dto.paymentSystem();
        String currency = dto.currency();

        List<Predicate> predicates = new ArrayList<>();

        if (maxBalance != null) {
            predicates.add(cb.ge(root.get("balance"), maxBalance));
        }
        if (minBalance != null) {
            predicates.add(cb.le(root.get("balance"), minBalance));
        }
        if (cardStatus != null) {
            predicates.add(cb.equal(root.get("status"), CardStatus.valueOf(cardStatus)));
        }
        if (paymentSystem != null) {
            predicates.add(cb.equal(root.get("system"), PaymentSystem.valueOf(paymentSystem)));
        }
        if (currency != null) {
            predicates.add(cb.equal(root.get("currency"), CurrencyType.valueOf(currency)));
        }

        return predicates;
    }

}

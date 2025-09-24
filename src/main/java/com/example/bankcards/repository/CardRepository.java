package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long> {

    List<Card> findByCardNumber( String cardNumber);

}

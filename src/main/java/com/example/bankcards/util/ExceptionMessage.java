package com.example.bankcards.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessage {

    public static final String USER_NOT_EXIST_BY_LOGIN = "Пользователь с логином: %s уже существует";
    public static final String ROLE_NOT_EXIST = "Роли : %s не существует";
    public static final String USER_NOT_FOUND_BY_ID = "Пользователя с идентификатором: %s не найдено";
    public static final String DUPLICATE_CARD = "Карта с номером - %s уже существует";
    public static final String USER_NOT_EXIST_BY_ID = "Пользователя с id: %s не существует";
    public static final String CARD_NOT_FOUND_BY_ID = "Карты с идентификатором: %s не найдено";
    public static final String CARDS_DOES_NOT_OWN_USER = "Выбранные карты не являются собственностью пользователя с идентификатором: %s";
    public static final String CARD_DOES_NOT_OWN_USER = "Карта с идентификатором:%s не является собственностью пользователя: %s";
    public static final String INSUFFICIENT_FUNDS_ON_THE_CARD = "Не достаточно средств на карте с идентификатором: %s";
    public static final String CARD_BLOCKED = "Карта с идентификатором: %s заблокирована";

}

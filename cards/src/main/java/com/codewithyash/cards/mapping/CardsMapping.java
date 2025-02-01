package com.codewithyash.cards.mapping;

import com.codewithyash.cards.dto.CardsDto;
import com.codewithyash.cards.entity.Cards;

public class CardsMapping {

    public static CardsDto mapToCardDto(CardsDto cardsDto, Cards cards) {
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
//        cardsDto.setCardId(cards.getCardId());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        return cardsDto;
    }

    public static Cards mapToCard(CardsDto cardsDto, Cards cards) {
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
//        cards.setCardId(cardsDto.getCardId());
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        return cards;
    }
}

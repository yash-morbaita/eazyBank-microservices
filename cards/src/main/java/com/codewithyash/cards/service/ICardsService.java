package com.codewithyash.cards.service;

import com.codewithyash.cards.dto.CardsDto;

public interface ICardsService {

    CardsDto fetchCardsDetails(String mobileNumber);

    void addNewCard(String mobileNumber);
    boolean updateCard(CardsDto cardsDto);
    boolean deleteCard(String mobileNumber);
}

package com.codewithyash.cards.service.impl;

import com.codewithyash.cards.constants.CardsConstants;
import com.codewithyash.cards.dto.CardsDto;
import com.codewithyash.cards.entity.Cards;
import com.codewithyash.cards.exception.CardAlreadyExistsException;
import com.codewithyash.cards.exception.ResourceNotFoundException;
import com.codewithyash.cards.mapping.CardsMapping;
import com.codewithyash.cards.repository.CardsRepository;
import com.codewithyash.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;
    @Override
    public CardsDto fetchCardsDetails(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card doesn't exist for the user")
        );
        CardsDto cardsDto = CardsMapping.mapToCardDto(new CardsDto(), cards);
        return cardsDto;
    }

    @Override
    public void addNewCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already exist for the number:" + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
         Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card doesn't exist"));
         Cards updatedCard = CardsMapping.mapToCard(cardsDto,cards);
         cardsRepository.save(updatedCard);
         return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card not Found")
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

    public Cards createNewCard(String mobileNumber) {
        Cards cards = new Cards();
        long randomCardNumber = 1000000000L + new Random().nextInt(900000000);
        cards.setCardNumber(Long.toString(randomCardNumber));
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setMobileNumber(mobileNumber);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return cards;
    }
}

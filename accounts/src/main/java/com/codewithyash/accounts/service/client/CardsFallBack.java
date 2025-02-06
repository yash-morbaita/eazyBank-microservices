package com.codewithyash.accounts.service.client;

import com.codewithyash.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallBack implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchAccounts(String correlationId, String mobileNumber) {
        return null;
    }
}

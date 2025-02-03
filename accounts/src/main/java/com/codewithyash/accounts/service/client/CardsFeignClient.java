package com.codewithyash.accounts.service.client;

import com.codewithyash.accounts.dto.CardsDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value = "cards/api/v1/fetch")
    ResponseEntity<CardsDto> fetchAccounts(@RequestParam
                                                  String mobileNumber);
}

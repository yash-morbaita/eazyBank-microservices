package com.codewithyash.accounts.service.client;

import com.codewithyash.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallBack.class)
public interface LoansFeignClient {
    @GetMapping("loans/api/v1/fetch")
    ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("eazybank-correlation-id")
                                              String correlationId, @RequestParam String mobileNumber);
}

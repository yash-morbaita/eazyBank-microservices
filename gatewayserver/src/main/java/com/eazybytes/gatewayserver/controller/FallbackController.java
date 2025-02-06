package com.eazybytes.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public Mono<String> sendFallback() {
        return Mono.just("An error occured. Please try after some time or contact support team");
    }

}

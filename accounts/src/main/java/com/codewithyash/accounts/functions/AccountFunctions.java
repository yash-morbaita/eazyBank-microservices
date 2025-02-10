package com.codewithyash.accounts.functions;

import com.codewithyash.accounts.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountFunctions {

    private static final Logger logger = LoggerFactory.getLogger(AccountFunctions.class);

//    bean autowaired automatically at runtime. dont need @Autowired
    @Bean
    public Consumer<Long> updateCommunication(IAccountService accountService) {
        return accountNumber -> {
            logger.debug("Updating Communication Status for the account Number: "+accountNumber.toString());
            accountService.updateCommunicationStatus(accountNumber);
        };
    }
}

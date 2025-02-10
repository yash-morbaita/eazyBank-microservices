package com.codewithyash.message.functions;

import com.codewithyash.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountsMsgDto,AccountsMsgDto> sendEmail() {
        return accountsMsgDto -> {
            System.out.println("Sending email with the details" + accountsMsgDto);
            return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto,Long> sendSms() {
        return accountsMsgDto -> {
            System.out.println("Sending sms with the details" + accountsMsgDto);
            return accountsMsgDto.accountNumber();
        };
    }
}

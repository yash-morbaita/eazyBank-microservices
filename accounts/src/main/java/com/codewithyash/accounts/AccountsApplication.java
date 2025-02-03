package com.codewithyash.accounts;

import com.codewithyash.accounts.dto.AccountsContactInfoPropertyDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoPropertyDto.class})
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Account microService REST API Documentation",
				description = "EasyBank Account microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Yash Morbita",
						email = "yashmorbaita@gmail.com",
						url = "https://codewithyash.com"
				),
				license = @License(
						name = "codeWithYash",
						url = "https://www.codewithyash.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EasyBank Account MicroService API Documentation",
				url = "codewithyash.com"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}

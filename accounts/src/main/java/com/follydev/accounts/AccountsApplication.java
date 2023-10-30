package com.follydev.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef="auditorAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				version = "1.0",
				description = "EazyBank Accounts microservice REST API Documentation",
				contact= @Contact(
				name = "FollyDev",
				email = "contact@follydev.com",
				url = "https://www.follydev.com"
		),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.eazbank.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EazyBank Accounts microservice Wiki Documentation",
				url = "https://www.eazybank.com"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}

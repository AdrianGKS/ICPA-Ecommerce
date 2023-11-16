package com.api.ICPAEcommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ICPA E-commmerce", version = "2", description = "API desevolvida para comércio de artigos religiosos"))
public class ICPAEcommerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ICPAEcommerceApplication.class, args);
	}
}

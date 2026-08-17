package com.api.ICPAEcommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "ICPA E-Commerce API",
				version = "1.0",
				description = "Documentando uma API para e-commerce religioso",
				contact = @Contact(name = "DevsFree", email = "devsfree@devsfree.com.br", url = "https://devsfree.com.br/")
		)
)
public class ICPAEcommerceApplication {
	public static void main(String[] args) {

		SpringApplication.run(ICPAEcommerceApplication.class, args);
	}
}

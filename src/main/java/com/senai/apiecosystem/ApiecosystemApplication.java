	package com.senai.apiecosystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =  @Info(title = "API Ecosystem & Recycle", version = "1.0.1", description = "Api para acesso dos dados da Ecosystem"))
public class ApiecosystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiecosystemApplication.class, args);
	}

}

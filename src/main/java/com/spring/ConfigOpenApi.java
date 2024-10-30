package com.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

@OpenAPIDefinition(info = @Info(title = "Indicador API", version = "v1", description = "Indicador API"))
@Configuration
public class ConfigOpenApi {

    @Value("${spring.application.version}")
    private String version;
    
    private static String title="TWA Sistemas - Indicador API";

    @Bean
    public OpenAPI customOpenAPI() {
	return new OpenAPI().components(new Components())
		.info(new io.swagger.v3.oas.models.info.Info().title(title).version(version));
    }


}
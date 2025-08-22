package com.example.simulator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// config/OpenApiConfig.java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("A&M Capital - Real Estate Simulator API")
                .version("1.0.0")
                .description("Endpoints simulateur de rentabilité, MeilleursAgents, AirDNA"));
    }
}

package com.example.simulator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@ConfigurationProperties(prefix = "amcors")
public class CorsConfig implements WebMvcConfigurer {

    private String allowedOrigins = "*"; // défaut

    public void setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(Arrays.stream(allowedOrigins.split(","))
                        .map(String::trim).toArray(String[]::new))
                .allowedMethods("GET","POST","OPTIONS");
    }
}

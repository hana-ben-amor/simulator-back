package com.example.simulator.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

// config/WebClientConfig.java (OkHttp utilisé directement dans services)
@Configuration
public class WebClientConfig {
    @Bean
    public okhttp3.OkHttpClient okHttpClient(@Value("${app.scraper.timeoutMs}") int timeoutMs) {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMillis(timeoutMs))
                .callTimeout(Duration.ofMillis(timeoutMs))
                .readTimeout(Duration.ofMillis(timeoutMs))
                .build();
    }
}


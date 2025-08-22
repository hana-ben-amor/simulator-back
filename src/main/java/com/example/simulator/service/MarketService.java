package com.example.simulator.service;

import com.example.simulator.util.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

// service/MarketService.java
@Service
@RequiredArgsConstructor
public class MarketService {
    private final MeilleursAgentsScraper scraper;
    private final FallbackDataService fallback;
    private String lastSource = "fallback";

    @Cacheable("rentData")
    public BigDecimal longTermRentPerM2(String city, RoomType rooms) {
        // 1) Scraper
        var scraped = scraper.fetchRentPerM2(city);
        double base = scraped.orElseGet(() -> fallback.rentPerM2(city).orElse(0));
        lastSource = scraped.isPresent() ? "MeilleursAgents" : "fallback";

        if (base <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not supported");

        // 2) Appliquer coefficient typologie LLD (Studio +39%, T2 100%, T3 -19%, T4 -20%) :contentReference[oaicite:11]{index=11}
        double coef = switch (rooms) {
            case STUDIO -> 1.39;
            case T2 -> 1.00;
            case T3 -> 0.81;
            case T4 -> 0.80;
        };
        return BigDecimal.valueOf(base * coef).setScale(2, RoundingMode.HALF_UP);
    }

    public String lastSource(){ return lastSource; }
}

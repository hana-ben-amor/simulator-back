package com.example.simulator.service;

import com.example.simulator.dto.AirbnbDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

// service/AirDnaClient.java
@Service
@RequiredArgsConstructor
public class AirDnaClient {
    private final OkHttpClient client;
    @Value("${app.airdna.baseUrl}") String baseUrl;
    @Value("${app.airdna.apiKey:}") String apiKey;
    @Value("${app.airdna.defaultOccupancy}") double defaultOcc;
    @Value("${app.airdna.multiplierVsLLD}") double multiplier;

    public Optional<AirbnbDataResponse> fetch(String city, double lldMonthly) {
        if (apiKey == null || apiKey.isBlank()) {
            // Fallback: ~3x LLD @ 70% occupancy (conforme cahier). :contentReference[oaicite:10]{index=10}
            var monthly = BigDecimal.valueOf(Math.max(lldMonthly * multiplier, lldMonthly));
            var adr = monthly.divide(BigDecimal.valueOf(30 * defaultOcc), 2, RoundingMode.HALF_UP);
            return Optional.of(new AirbnbDataResponse(city, adr, defaultOcc, monthly, "fallback"));
        }
        // Exemple d’appel – adapter au vrai endpoint AirDNA si clé dispo.
        var url = HttpUrl.parse(baseUrl + "/market/adr")
                .newBuilder().addQueryParameter("city", city).build();
        var req = new Request.Builder().url(url).header("apikey", apiKey).build();
        try (var resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful() || resp.body()==null) return Optional.empty();
            var json = new ObjectMapper().readTree(resp.body().string());
            var adr = json.path("adr").asDouble(0);
            var monthly = BigDecimal.valueOf(adr * 30 * defaultOcc);
            return Optional.of(new AirbnbDataResponse(city, BigDecimal.valueOf(adr), defaultOcc, monthly, "AirDNA"));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

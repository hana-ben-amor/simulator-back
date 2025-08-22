package com.example.simulator.service.ai;

import com.example.simulator.dto.ai.AiContext;
import com.example.simulator.dto.ai.ChatRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.ai.provider", havingValue = "GEMINI", matchIfMissing = true)
public class GeminiProvider implements AiProvider {

    @Value("${gemini.apiKey:}")
    private String apiKey;

    @Value("${app.ai.model:gemini-1.5-flash}")
    private String model;

    @Value("${app.ai.temperature:0.2}")
    private double temperature;

    private final ObjectMapper om = new ObjectMapper();

    private OkHttpClient client() {
        return new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(45))
                .readTimeout(Duration.ofSeconds(45))
                .build();
    }

    @Override
    public String chat(ChatRequest req) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GEMINI_API_KEY manquant");
        }

        String system = "You are a helpful assistant specialized in real-estate ROI simulation.";
        AiContext ctx = req.context();
        if (ctx != null && ctx.system() != null && !ctx.system().isBlank()) {
            system = ctx.system();
        }

        try {
            // Payload REST de l'API Generative Language
            var payload = Map.of(
                    "systemInstruction", Map.of(
                            "role", "system",
                            "parts", List.of(Map.of("text", system))
                    ),
                    "contents", List.of(Map.of(
                            "role", "user",
                            "parts", List.of(Map.of("text", req.message()))
                    )),
                    "generationConfig", Map.of(
                            "temperature", temperature,
                            "maxOutputTokens", 1024
                    )
            );

            var body = RequestBody.create(
                    om.writeValueAsBytes(payload),
                    MediaType.parse("application/json")
            );

            var url = "https://generativelanguage.googleapis.com/v1beta/models/"
                    + model + ":generateContent?key=" + apiKey;

            var request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Content-Type", "application/json")
                    .build();

            try (var resp = client().newCall(request).execute()) {
                var respText = resp.body() != null ? resp.body().string() : "";
                if (!resp.isSuccessful()) {
                    log.error("Gemini error {}: {}", resp.code(), respText);
                    throw new RuntimeException("Gemini error " + resp.code() + ": " + respText);
                }
                var json = om.readTree(respText);
                // candidates[0].content.parts[0].text
                var candidates = json.path("candidates");
                if (candidates.isArray() && candidates.size() > 0) {
                    var parts = candidates.get(0).path("content").path("parts");
                    if (parts.isArray() && parts.size() > 0) {
                        return parts.get(0).path("text").asText("");
                    }
                }
                return "";
            }
        } catch (Exception e) {
            log.error("Gemini call failed", e);
            throw new RuntimeException("Gemini call failed: " + e.getMessage(), e);
        }
    }
}

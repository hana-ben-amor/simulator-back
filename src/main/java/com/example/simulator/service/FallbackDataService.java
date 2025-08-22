package com.example.simulator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

// service/FallbackDataService.java
@Service
@RequiredArgsConstructor
public class FallbackDataService {
    private final ObjectMapper om = new ObjectMapper();
    private Map<String, Double> rents;

    @PostConstruct
    void load() {
        try (var in = new ClassPathResource("data/rents.json").getInputStream()) {
            var tree = om.readTree(in);
            rents = new HashMap<>();
            tree.fields().forEachRemaining(e -> rents.put(e.getKey().toUpperCase(), e.getValue().get("rentPerM2").asDouble()));
        } catch (IOException e) {
            rents = Map.of(); // safe default
        }
    }

    public OptionalDouble rentPerM2(String city) {
        var v = rents.get(city.toUpperCase());
        return v == null ? OptionalDouble.empty() : OptionalDouble.of(v);
    }

    public List<String> cities(){
        return rents.keySet().stream().sorted().toList();
    }
}

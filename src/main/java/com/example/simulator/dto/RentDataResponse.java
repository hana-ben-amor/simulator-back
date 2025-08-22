package com.example.simulator.dto;

// dto/RentDataResponse.java
public record RentDataResponse(String city, java.math.BigDecimal rentPerM2, String source, java.time.Instant fetchedAt) {}


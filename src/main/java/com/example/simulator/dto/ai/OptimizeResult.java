package com.example.simulator.dto.ai;

public record OptimizeResult(String city, double price, double rentPerM2,
                             double monthlyRevenue, double grossYieldPct,
                             String rationale) {}

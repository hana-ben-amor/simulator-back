package com.example.simulator.dto;
// dto/SimulationResult.java
import java.math.BigDecimal;

public record SimulationResult(
        BigDecimal totalInvestment,
        BigDecimal monthlyRevenue,
        BigDecimal grossYieldPct,
        String dataSource
) {}

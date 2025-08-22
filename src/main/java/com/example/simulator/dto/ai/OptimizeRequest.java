package com.example.simulator.dto.ai;

import java.util.List;

public record OptimizeRequest(double targetGrossYieldPct,
                              String rooms, int surface, List<String> cities,
                              double maxPrice) {}

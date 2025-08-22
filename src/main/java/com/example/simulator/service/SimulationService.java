package com.example.simulator.service;

import com.example.simulator.dto.SimulationRequest;
import com.example.simulator.dto.SimulationResult;
import com.example.simulator.util.Fees;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

// service/SimulationService.java
@Service
@RequiredArgsConstructor
public class SimulationService {
    private final MarketService market;
    private final AirDnaClient airDna;

    public SimulationResult simulate(SimulationRequest req) {
        boolean isShort = "COURTE".equalsIgnoreCase(req.exploitationType());
        var fees = Fees.compute(req.price(), req.surface(), isShort);
        var totalInvestment = req.price().add(fees.total());

        BigDecimal monthly;
        String dataSource;
        if (!isShort) {
            var rentM2 = market.longTermRentPerM2(req.city(), req.rooms());
            monthly = rentM2.multiply(BigDecimal.valueOf(req.surface()));
            dataSource = market.lastSource();
        } else {
            // Besoin du LLD pour le fallback courte durée (~3x LLD @ 70%) :contentReference[oaicite:12]{index=12}
            var lldM2 = market.longTermRentPerM2(req.city(), req.rooms());
            var lldMonthly = lldM2.multiply(BigDecimal.valueOf(req.surface())).doubleValue();
            var air = airDna.fetch(req.city(), lldMonthly)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY, "AirDNA unavailable"));
            monthly = air.monthly();
            dataSource = air.source();
        }

        var grossYieldPct = monthly.multiply(BigDecimal.valueOf(12))
                .divide(totalInvestment, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);

        return new SimulationResult(totalInvestment, monthly.setScale(2, RoundingMode.HALF_UP), grossYieldPct, dataSource);
    }
}

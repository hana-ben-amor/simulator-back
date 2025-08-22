package com.example.simulator.util;

// util/Fees.java
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Fees {
    private Fees(){}
    public record Breakdown(BigDecimal notary, BigDecimal commission, BigDecimal architect, BigDecimal total) {}
    public static Breakdown compute(BigDecimal price, int surface, boolean shortTerm) {
        var notary = price.multiply(BigDecimal.valueOf(0.09));          // 9% :contentReference[oaicite:6]{index=6}
        var commission = price.multiply(BigDecimal.valueOf(0.085));      // 8.5% :contentReference[oaicite:7]{index=7}
        var perM2 = shortTerm ? 120 : 90;                                // 90/120 €/m² :contentReference[oaicite:8]{index=8}
        var architect = BigDecimal.valueOf(perM2).multiply(BigDecimal.valueOf(surface));
        var total = notary.add(commission).add(architect);
        return new Breakdown(scale(notary), scale(commission), scale(architect), scale(total));
    }
    private static BigDecimal scale(BigDecimal v){ return v.setScale(2, RoundingMode.HALF_UP); }
}

package com.example.simulator.service;
import com.example.simulator.dto.ai.OptimizeRequest;
import com.example.simulator.dto.ai.OptimizeResult;
import com.example.simulator.util.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service @RequiredArgsConstructor
public class OptimizationService {
    private final MarketService market;
    public OptimizeResult search(OptimizeRequest r){
        // grille simple sur price autour de maxPrice et sur villes données
        double bestDiff = 1e9; OptimizeResult best=null;
        for (var city: r.cities()){
            for (double price = r.maxPrice()*0.6; price<=r.maxPrice(); price+= Math.max(10000, r.maxPrice()*0.05)){
                var m2 = market.longTermRentPerM2(city, RoomType.valueOf(r.rooms()));
                double monthly = m2.doubleValue()* r.surface();
                double gross = (monthly*12d)/price*100d;
                double diff = Math.abs(gross - r.targetGrossYieldPct());
                if (diff < bestDiff){
                    bestDiff = diff;
                    best = new OptimizeResult(city, price, m2.doubleValue(), monthly, gross,
                            "Selected by grid search: balance between price and rent/m².");
                }
            }
        }
        return best;
    }
}

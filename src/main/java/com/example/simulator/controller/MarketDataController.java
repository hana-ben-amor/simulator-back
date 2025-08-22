package com.example.simulator.controller;

import com.example.simulator.dto.AirbnbDataResponse;
import com.example.simulator.dto.RentDataResponse;
import com.example.simulator.service.AirDnaClient;
import com.example.simulator.service.FallbackDataService;
import com.example.simulator.service.MarketService;
import com.example.simulator.util.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

// controller/MarketDataController.java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MarketDataController {
    private final MarketService market;
    private final AirDnaClient airDna;
    private final FallbackDataService fallback;

/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Long-term rent data (LLD) for a given city and room type.
     *
     * @param city City name (e.g. "Paris", "Lyon", etc.)
     * @param rooms Room type (e.g. STUDIO, T2, etc.)
     * @return LLD in euros per square meter, together with the source of this data.
     */
/* <<<<<<<<<<  159a1e4e-ae15-45c2-8a8a-575812f8746f  >>>>>>>>>>> */
    @GetMapping("/rent-data")
    public RentDataResponse rentData(@RequestParam String city, @RequestParam RoomType rooms) {
        var start = java.time.Instant.now();
        var m2 = market.longTermRentPerM2(city, rooms);
        return new RentDataResponse(city.toUpperCase(), m2, market.lastSource(), start);
    }

    @GetMapping("/airbnb-data")
    public AirbnbDataResponse airbnbData(@RequestParam String city, @RequestParam RoomType rooms, @RequestParam int surface) {
        var lldM2 = market.longTermRentPerM2(city, rooms);
        var lldMonthly = lldM2.multiply(BigDecimal.valueOf(surface)).doubleValue();
        return airDna.fetch(city, lldMonthly)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY, "AirDNA unavailable"));
    }

    @GetMapping("/cities")
    public List<String> cities() { return fallback.cities(); }
}

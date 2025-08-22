package com.example.simulator.controller;

import com.example.simulator.dto.SimulationRequest;
import com.example.simulator.dto.SimulationResult;
import com.example.simulator.service.SimulationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// controller/SimulationController.java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SimulationController {
    private final SimulationService service;

    @PostMapping("/simulate")
    public SimulationResult simulate(@Valid @RequestBody SimulationRequest req) {
        var sw = System.nanoTime();
        var res = service.simulate(req);
        // Ici tu peux tracer/monitorer pour respecter la contrainte perf globale (<2s côté endpoints). :contentReference[oaicite:13]{index=13}
        var elapsedMs = (System.nanoTime()-sw)/1_000_000;
        return res;
    }
}

package com.example.simulator.controller;

import com.example.simulator.dto.ai.OptimizeRequest;
import com.example.simulator.dto.ai.OptimizeResult;
import com.example.simulator.service.OptimizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/optimize")
@RequiredArgsConstructor
public class OptimizationController {
    private final OptimizationService optimization;

    @PostMapping
    public OptimizeResult optimize(@RequestBody OptimizeRequest request) {
        return optimization.search(request);
    }
}

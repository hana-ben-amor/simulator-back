package com.example.simulator.dto.ai;

/** Contexte optionnel pour enrichir les prompts (tout est nullable). */
public record AiContext(
        String system,  // prompt système optionnel
        String city,
        String rooms,
        Integer surface
) {}

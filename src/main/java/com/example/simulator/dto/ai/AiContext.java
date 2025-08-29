package com.example.simulator.dto.ai;

/** Contexte optionnel pour enrichir les prompts (tout est nullable). */
public record AiContext(
        //system càd Tu es un conseiller en investissement immobilier.
        String system,  // prompt système optionnel
        String city,
        String rooms,
        Integer surface
) {}

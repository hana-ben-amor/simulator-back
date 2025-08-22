package com.example.simulator.dto;

import com.example.simulator.util.RoomType;
import jakarta.validation.constraints.*;

public record SimulationRequest(
        @NotNull @DecimalMin("50000") @DecimalMax("1000000") java.math.BigDecimal price,
        @Min(10) @Max(200) int surface,
        @NotNull RoomType rooms,
        @NotBlank String city,
        @NotBlank String exploitationType // "LLD" ou "COURTE"
) {}

package com.kassensystem.dto.kasse;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BonRequest(
        @NotEmpty List<@Valid BonPositionRequest> positionen,
        @NotNull @DecimalMin("0.0") BigDecimal gegebenesGeld) {
}

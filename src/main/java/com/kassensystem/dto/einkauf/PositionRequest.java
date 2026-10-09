package com.kassensystem.dto.einkauf;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PositionRequest(
        @NotBlank String produktnummer,
        @Positive int menge) {
}

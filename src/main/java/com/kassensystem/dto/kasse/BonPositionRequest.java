package com.kassensystem.dto.kasse;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BonPositionRequest(
        int einkaufsnummer,
        @NotBlank String produktnummer,
        @NotNull Timestamp zeitpunkt,
        @Positive int menge) {
}

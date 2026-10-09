package com.kassensystem.dto.produkt;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.kassensystem.fachkonzept.Steuersatz;

public record ProduktUpdateRequest(
        @NotBlank String bezeichnung,
        @NotNull @DecimalMin("0.0") BigDecimal verkaufspreis,
        @NotNull @DecimalMin("0.0") BigDecimal bestand,
        @NotNull Steuersatz steuersatz,
        @NotNull Boolean archiviert) {
}

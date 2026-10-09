package com.kassensystem.dto.produkt;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;

import com.kassensystem.fachkonzept.Steuersatz;

public record ProduktPatchRequest(
        String bezeichnung,
        @DecimalMin("0.0") BigDecimal verkaufspreis,
        @DecimalMin("0.0") BigDecimal bestand,
        Steuersatz steuersatz,
        Boolean archiviert) {
}

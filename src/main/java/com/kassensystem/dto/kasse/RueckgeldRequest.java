package com.kassensystem.dto.kasse;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record RueckgeldRequest(
        @NotNull @DecimalMin("0.0") BigDecimal bezahlterBetrag,
        @NotNull @DecimalMin("0.0") BigDecimal gesamtbetrag) {
}

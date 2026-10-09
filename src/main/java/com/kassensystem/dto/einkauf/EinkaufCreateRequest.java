package com.kassensystem.dto.einkauf;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record
EinkaufCreateRequest(@NotEmpty List<@Valid PositionRequest> positionen) {}

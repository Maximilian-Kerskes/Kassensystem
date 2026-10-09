package com.kassensystem.dto.bericht;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record
UmsatzGetRequest(
	@Valid String startDate,
    @Valid String endDate
) {}

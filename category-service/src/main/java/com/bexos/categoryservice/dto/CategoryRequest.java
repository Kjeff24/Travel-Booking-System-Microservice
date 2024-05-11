package com.bexos.categoryservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CategoryRequest(
        @NotEmpty(message = "Name field is required")
        String name,
        String description,
        @NotEmpty(message = "Code field is required and should be either ACC, FLI, CAR, or HOT")
        String code
) {
}

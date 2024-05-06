package com.bexos.categoryservice.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(
        @NotEmpty(message = "Name field is required")
        String name,
        String description,
        @NotEmpty(message = "This field is required and should be either ACC, FLI, CAR, or HOT")
        String code,
        String icon
) {
}

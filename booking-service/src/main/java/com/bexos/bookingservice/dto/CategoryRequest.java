package com.bexos.bookingservice.dto;

import com.bexos.bookingservice.models.CategoryCode;
import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(
        @NotEmpty(message = "This field is required")
        String name,
        String description,
        @NotEmpty(message = "This field is required and should be either ACC, FLI, CAR, or HOT")
        String code,
        String icon
) {
}

package com.bexos.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AccommodationRequest(
        @NotEmpty(message = "This field is required")
        String location,
        @NotEmpty(message = "This field is required")
        String type,
        @NotEmpty(message = "This field is required")
        String capacity,
        @NotNull(message = "This field is required")
        double price,
        @NotEmpty(message = "This field is required")
        String categoryId
) {
}

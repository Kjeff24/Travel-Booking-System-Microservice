package com.bexos.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AccommodationRequest(
        @NotEmpty(message = "Location field is required")
        String location,
        @NotEmpty(message = "Type field is required")
        String type,
        @NotEmpty(message = "Capacity field is required")
        String capacity,
        @NotNull(message = "Price field is required")
        double price,
        @NotEmpty(message = "Category field is required")
        String categoryId
) {
}

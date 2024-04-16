package com.bexos.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FlightRequest(
        @NotEmpty(message = "This field is required")
        String departureCity,
        @NotEmpty(message = "This field is required")
        String destinationCity,
        @NotNull(message = "This field is required")
        double price,
        @NotEmpty(message = "This field is required")
        String categoryId
) {
}

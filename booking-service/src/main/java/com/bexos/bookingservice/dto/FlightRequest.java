package com.bexos.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FlightRequest(
        @NotEmpty(message = "Departure City field is required")
        String departureCity,
        @NotEmpty(message = "Destination City field is required")
        String destinationCity,
        @NotNull(message = "Price field is required")
        double price,
        @NotEmpty(message = "Date field is required")
        String date,
        @NotEmpty(message = "Category field is required")
        String categoryId
) {
}

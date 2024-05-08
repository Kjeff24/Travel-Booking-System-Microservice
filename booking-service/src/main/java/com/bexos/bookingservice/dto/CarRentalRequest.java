package com.bexos.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CarRentalRequest(
        @NotEmpty(message = "Car type field is required")
        String carType,
        @NotNull(message = "Price field is required")
        double price,
        @NotEmpty(message = "Category field is required")
        String categoryId
) {
}

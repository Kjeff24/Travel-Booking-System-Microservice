package com.bexos.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CarRentalRequest(
        @NotEmpty(message = "This field is required")
        String carType,
        @NotNull(message = "This field is required")
        double pricePerDay,
        @NotEmpty(message = "This field is required")
        String carImage,
        @NotEmpty(message = "This field is required")
        String categoryId
) {
}

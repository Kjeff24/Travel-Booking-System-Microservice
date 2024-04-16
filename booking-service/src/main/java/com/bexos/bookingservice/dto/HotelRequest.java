package com.bexos.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record HotelRequest(
        @NotEmpty(message = "This field is required")
        String hotelName,
        @NotEmpty(message = "This field is required")
        String location,
        @NotEmpty(message = "This field is required")
        String roomType,
        @NotNull(message = "This field is required")
        double price,
        @NotEmpty(message = "This field is required")
        String categoryId
) {
}

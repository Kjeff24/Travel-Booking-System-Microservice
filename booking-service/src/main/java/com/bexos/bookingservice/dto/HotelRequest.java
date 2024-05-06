package com.bexos.bookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record HotelRequest(
        @NotEmpty(message = "Hotel name field is required")
        String hotelName,
        @NotEmpty(message = "Location field is required")
        String location,
        @NotEmpty(message = "Room type field is required")
        String roomType,
        @NotNull(message = "Price field is required")
        double price,
        @NotEmpty(message = "Category field is required")
        String categoryId
) {
}

package com.bexos.bookingservice.dto;

public record AccommodationRequest(
        String location,
        String type,
        String capacity,
        double price
) {
}

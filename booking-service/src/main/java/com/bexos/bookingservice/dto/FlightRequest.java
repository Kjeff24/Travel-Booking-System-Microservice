package com.bexos.bookingservice.dto;

import java.time.LocalDateTime;

public record FlightRequest(
        String departureCity,
        String destinationCity,
        LocalDateTime date,
        double price
) {
}

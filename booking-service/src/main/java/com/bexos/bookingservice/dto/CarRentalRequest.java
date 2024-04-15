package com.bexos.bookingservice.dto;

public record CarRentalRequest(
        String carType,
        double pricePerDay,
        String carImage
) {
}

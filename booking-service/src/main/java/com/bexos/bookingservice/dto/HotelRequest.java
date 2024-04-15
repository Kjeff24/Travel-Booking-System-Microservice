package com.bexos.bookingservice.dto;

public record HotelRequest(
        String hotelName,
        String location,
        String roomType,
        double price
) {
}

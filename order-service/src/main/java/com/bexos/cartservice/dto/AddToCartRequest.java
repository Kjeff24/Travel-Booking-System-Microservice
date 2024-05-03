package com.bexos.cartservice.dto;

public record AddToCartRequest(
        String userId,
        String bookingId,
        int price
) {
}

package com.bexos.cartservice.dto;

import com.bexos.cartservice.models.CartItem;

import java.util.List;

public record AddAllToCartRequest(
        String bookingId,
        int quantity,
        double totalPrice
) {
}

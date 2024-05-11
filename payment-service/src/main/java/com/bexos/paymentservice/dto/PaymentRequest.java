package com.bexos.paymentservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PaymentRequest(
        String email,
        int exp_month,
        int exp_year,
        int last4,
        String cardId,
        String userId,
        int totalCost,
        List<CartItem> cartItems

) {
}

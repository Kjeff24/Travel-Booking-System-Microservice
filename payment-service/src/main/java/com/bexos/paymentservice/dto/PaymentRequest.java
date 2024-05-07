package com.bexos.paymentservice.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PaymentRequest(
        String email,
        int exp_month,
        int exp_year,
        int last4,
        String paymentId,
        String userId,
        int totalCost,
        List<CartItem> cartItems

) {
}

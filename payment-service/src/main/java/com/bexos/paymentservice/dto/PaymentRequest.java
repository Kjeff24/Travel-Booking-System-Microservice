package com.bexos.paymentservice.dto;

import jakarta.validation.constraints.NotEmpty;

public record PaymentRequest(
        @NotEmpty(message = "email is required")
        String email,
        @NotEmpty(message = "Expiration month is required")
        int exp_month,
        @NotEmpty(message = "Expiration year is required")
        int exp_year,
        @NotEmpty(message = "Last 4 digit of card number required")
        int last4
) {
}

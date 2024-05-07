package com.bexos.paymentservice.dto;

import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
public record CartItem (
        String id,
        String bookingId,
        int quantity,
        String orderId,
        double totalPrice
){}

package com.bexos.cartservice.mappers;

import com.bexos.cartservice.models.CartItem;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public CartItem toNewOrderItem(String bookingId, int quantity, String orderId){
        return CartItem.builder()
                .bookingId(bookingId)
                .quantity(quantity)
                .orderId(orderId)
                .build();
    }
}

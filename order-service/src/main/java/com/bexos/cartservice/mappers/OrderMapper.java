package com.bexos.cartservice.mappers;

import com.bexos.cartservice.models.Order;
import com.bexos.cartservice.models.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public OrderItem toNewOrderItem(String bookingId, int quantity, String orderId){
        return OrderItem.builder()
                .bookingId(bookingId)
                .quantity(quantity)
                .orderId(orderId)
                .build();
    }
}

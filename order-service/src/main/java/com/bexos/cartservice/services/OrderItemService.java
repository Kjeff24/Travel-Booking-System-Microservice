package com.bexos.cartservice.services;

import com.bexos.cartservice.models.OrderItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderItemService {

    ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(String orderId);

    ResponseEntity<OrderItem> addOrderItem(String orderId, OrderItem orderItem);

    void removeOrderItem(String orderId, String bookingId);

    void updateOrderItemQuantity(String orderId, String productId, int newQuantity);
}

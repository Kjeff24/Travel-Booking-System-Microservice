package com.bexos.cartservice.services;

import com.bexos.cartservice.models.OrderItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderItemService {

    ResponseEntity<OrderItem> addOrderItem(String orderId, OrderItem orderItem);

    ResponseEntity<OrderItem> findOrderItemById(String orderItemId);
//    ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(String orderId);


//    void removeOrderItem(String orderId, String bookingId);
//
//    void updateOrderItemQuantity(String orderId, String productId, int newQuantity);
}

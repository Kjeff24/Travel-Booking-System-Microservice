package com.bexos.cartservice.services;

import com.bexos.cartservice.models.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    ResponseEntity<Order> createOrder(Order order);

    ResponseEntity<List<Order>> getAllOrders();

    ResponseEntity<Order> getOrderById(String orderId);

    ResponseEntity<Order> findOrderByUserId(String userId);
    void updateOrder(Order Updatedorder);

    void deleteOrder(String orderId);

    boolean existByUserId(String userId);
}

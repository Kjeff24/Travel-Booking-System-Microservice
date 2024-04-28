package com.bexos.cartservice.services;

import com.bexos.cartservice.models.Order;
import com.bexos.cartservice.models.OrderItem;
import com.bexos.cartservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements  OrderItemService{

    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null) {
            List<OrderItem> orderItems = order.getItems();
            return ResponseEntity.ok(orderItems);
        }
        return null;
    }

    @Override
    public ResponseEntity<OrderItem> addOrderItem(String orderId, OrderItem orderItem) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.getItems().add(orderItem);
            orderRepository.save(order);
            return ResponseEntity.ok(orderItem);
        }
        return null;
    }

    @Override
    public void removeOrderItem(String orderId, String bookingId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.getItems().removeIf(item -> item.getBookingId().equals(bookingId));
            orderRepository.save(order);
        }
    }

    @Override
    public void updateOrderItemQuantity(String orderId, String productId, int newQuantity) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            List<OrderItem> items = order.getItems();
            for (OrderItem item : items) {
                if (item.getBookingId().equals(productId)) {
                    item.setQuantity(newQuantity);
                    break;
                }
            }
            orderRepository.save(order);
        }
    }
}

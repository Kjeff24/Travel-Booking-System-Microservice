package com.bexos.cartservice.services;

import com.bexos.cartservice.models.OrderItem;
import com.bexos.cartservice.repositories.OrderItemRepository;
import com.bexos.cartservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public ResponseEntity<OrderItem> addOrderItem(OrderItem orderItem) {
        OrderItem orderItemSaved = orderItemRepository.save(orderItem);
        return ResponseEntity.ok(orderItemSaved);
    }

    public ResponseEntity<OrderItem> findOrderItemById(String orderItemId) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderItemId);
        return orderItem.map(ResponseEntity::ok).orElse(null);
    }

    public ResponseEntity<OrderItem> updateOrderItemQuantity(String orderItemId, OrderItem orderItem) {
        Optional<OrderItem> existingOrderItemOptional = orderItemRepository.findById(orderItemId);
        if (existingOrderItemOptional.isPresent()) {
            OrderItem existingOrderItem = existingOrderItemOptional.get();
            existingOrderItem.setQuantity(orderItem.getQuantity());
            OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);
            return ResponseEntity.ok(updatedOrderItem);
        }
        return ResponseEntity.notFound().build();
    }


//    @Override
//    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(String orderId) {
//        Order order = orderRepository.findById(orderId).orElse(null);
//
//        if (order != null) {
//            List<OrderItem> orderItems = order.getItems();
//            return ResponseEntity.ok(orderItems);
//        }
//        return null;
//    }

//    public ResponseEntity<OrderItem> addOrderItem(String orderId, OrderItem orderItem) {
//        Order order = orderRepository.findById(orderId).orElse(null);
//        if (order != null) {
//            order.getItems().add(orderItem);
//            orderRepository.save(order);
//            return ResponseEntity.ok(orderItem);
//        }
//        return null;
//    }

//    @Override
//    public void removeOrderItem(String orderId, String bookingId) {
//        Order order = orderRepository.findById(orderId).orElse(null);
//        if (order != null) {
//            order.getItems().removeIf(item -> item.getBookingId().equals(bookingId));
//            orderRepository.save(order);
//        }
//    }
//
//    @Override
//    public void updateOrderItemQuantity(String orderId, String productId, int newQuantity) {
//        Order order = orderRepository.findById(orderId).orElse(null);
//        if (order != null) {
//            List<OrderItem> items = order.getItems();
//            for (OrderItem item : items) {
//                if (item.getBookingId().equals(productId)) {
//                    item.setQuantity(newQuantity);
//                    break;
//                }
//            }
//            orderRepository.save(order);
//        }
//    }
}

package com.bexos.cartservice.services;

import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.mappers.OrderMapper;
import com.bexos.cartservice.models.Order;
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
    private final OrderMapper orderMapper;


    public ResponseEntity<OrderItem> addToCart(AddToCartRequest request) {
        String userId = request.userId();
        String bookingId = request.bookingId();
        Optional<OrderItem> existingOrderItemOptional = orderItemRepository.findByBookingId(bookingId);
        if (existingOrderItemOptional.isPresent()) {
            return ResponseEntity.ok(updateOrderItemQuantity(existingOrderItemOptional.get()));
        }
        Optional<Order> existingOrderOptional = orderRepository.findByUserId(userId);
        if (existingOrderOptional.isPresent()) {
            return ResponseEntity.ok(createNewOrderItem(bookingId, existingOrderOptional.get().getId()));
        }

        Order savedOrder = orderRepository.save(Order.builder().userId(userId).build());
        return ResponseEntity.ok(createNewOrderItem(bookingId, savedOrder.getId()));
    }

    private OrderItem updateOrderItemQuantity(OrderItem existingOrderItem) {
        existingOrderItem.setQuantity(existingOrderItem.getQuantity() + 1);
        return orderItemRepository.save(existingOrderItem);
    }

    private OrderItem createNewOrderItem(String bookingId, String orderId) {
        return orderItemRepository.save(orderMapper.toNewOrderItem(bookingId, 1, orderId));
    }


}

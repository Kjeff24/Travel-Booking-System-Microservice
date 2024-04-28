package com.bexos.cartservice.controllers;

import com.bexos.cartservice.models.Order;
import com.bexos.cartservice.models.OrderItem;
import com.bexos.cartservice.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-service/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItem(@PathVariable("orderId") String orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<OrderItem> addOrderItem(@PathVariable("orderId") String orderId, @RequestBody OrderItem orderItem) {
        return orderItemService.addOrderItem(orderId, orderItem);
    }

    @PutMapping("/{orderId}/{bookingId}")
    public void updateOrderItemQuantity(@PathVariable("orderId") String orderId, @PathVariable("bookingId") String productId, @RequestBody Integer quantity) {
        orderItemService.updateOrderItemQuantity(orderId, productId, quantity);
    }

    @DeleteMapping("/{orderId}/{bookingId}")
    public void deleteOrderItem(@PathVariable("orderId") String orderId, @PathVariable("bookingId") String bookingId) {
        orderItemService.removeOrderItem(orderId, bookingId);
    }
}

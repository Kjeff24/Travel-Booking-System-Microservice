package com.bexos.cartservice.controllers;

import com.bexos.cartservice.models.Order;
import com.bexos.cartservice.models.OrderItem;
import com.bexos.cartservice.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-service/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItem> addOrderItem(@RequestBody OrderItem orderItem) {

        return orderItemService.addOrderItem(orderItem);
    }

    @GetMapping("find-by-id/{orderItemId}")
    public ResponseEntity<OrderItem> findOrderItemById(@PathVariable String orderItemId) {
        return orderItemService.findOrderItemById(orderItemId);
    }

    @PatchMapping("update-order-quantity/{orderItemId}")
    public ResponseEntity<OrderItem> updateOrderItemQuantity(@PathVariable String orderItemId, @RequestBody OrderItem orderItem) {
        return orderItemService.updateOrderItemQuantity(orderItemId, orderItem);
    }



//    @GetMapping("/{orderId}")
//    public ResponseEntity<List<OrderItem>> getOrderItem(@PathVariable("orderId") String orderId) {
//        return orderItemService.getOrderItemsByOrderId(orderId);
//    }
//

//
//    @PutMapping("/{orderId}/{bookingId}")
//    public void updateOrderItemQuantity(@PathVariable("orderId") String orderId, @PathVariable("bookingId") String productId, @RequestBody Integer quantity) {
//        orderItemService.updateOrderItemQuantity(orderId, productId, quantity);
//    }
//
//    @DeleteMapping("/{orderId}/{bookingId}")
//    public void deleteOrderItem(@PathVariable("orderId") String orderId, @PathVariable("bookingId") String bookingId) {
//        orderItemService.removeOrderItem(orderId, bookingId);
//    }
}

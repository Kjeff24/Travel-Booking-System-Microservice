package com.bexos.cartservice.controllers;

import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.models.OrderItem;
import com.bexos.cartservice.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-service/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<OrderItem> addToCart(@RequestBody AddToCartRequest request) {
        return orderItemService.addToCart(request);
    }
}

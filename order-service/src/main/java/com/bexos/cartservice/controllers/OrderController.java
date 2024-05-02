package com.bexos.cartservice.controllers;

import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.models.CartItem;
import com.bexos.cartservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-service/order-items")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<CartItem> addToCart(@RequestBody AddToCartRequest request) {
        return orderService.addToCart(request);
    }

    @GetMapping("/get-order-quantity/{userId}")
    public ResponseEntity<Integer> findCartItemsQuantity(@PathVariable String userId) {
        return orderService.findCartItemsQuantity(userId);
    }

    @GetMapping("/get-cart-items/{userId}")
    public ResponseEntity<List<CartItem>> findCartItems(@PathVariable String userId) {
        return orderService.findCartItems(userId);
    }
}

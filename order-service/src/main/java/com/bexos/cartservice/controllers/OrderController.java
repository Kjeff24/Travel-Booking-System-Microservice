package com.bexos.cartservice.controllers;

import com.bexos.cartservice.dto.AddAllToCartRequest;
import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.models.CartItem;
import com.bexos.cartservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/add-to-cart")
    public ResponseEntity<CartItem> addToCart(@RequestBody AddToCartRequest request) {
        return orderService.addToCart(request);
    }

    @PostMapping("/add-all-to-cart/{userId}")
    public ResponseEntity<List<CartItem>> addAllCartItems(@RequestBody List<AddAllToCartRequest> request, @PathVariable String userId) {
        return orderService.addAllCartItems(request, userId);
    }

    @DeleteMapping("/delete-all-cart-items/{userId}")
    public ResponseEntity<Void> deleteAllCartItems(@PathVariable String userId) {
        return orderService.deleteAllCartItems(userId);
    }

    @DeleteMapping("/delete-from-cart")
    public ResponseEntity<Void> deleteFromCart(@RequestParam("bookingId") String bookingId, @RequestParam("userId") String userId) {
        return orderService.deleteCartItemByBookingIdAndOrderId(bookingId, userId);
    }

    @PatchMapping("/decrease-cart-item")
    public ResponseEntity<CartItem> decreaseCartItem(@RequestBody AddToCartRequest request) {
        return orderService.decreaseCartItem(request);
    }


    @GetMapping("/get-order-quantity/{userId}")
    public ResponseEntity<Integer> findCartItemsQuantity(@PathVariable String userId) {
        return orderService.findCartItemsQuantity(userId);
    }
    @GetMapping("/get-total-price/{userId}")
    public ResponseEntity<Integer> findCartItemsTotalPrice(@PathVariable String userId) {
        return orderService.findCartItemsTotalPrice(userId);
    }

    @GetMapping("/get-cart-items/{userId}")
    public ResponseEntity<List<CartItem>> findCartItems(@PathVariable String userId) {
        return orderService.findCartItems(userId);
    }
}

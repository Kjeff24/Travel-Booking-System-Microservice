package com.bexos.cartservice.services;

import com.bexos.cartservice.dto.AddAllToCartRequest;
import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.models.CartItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    ResponseEntity<CartItem> addToCart(AddToCartRequest request);

    ResponseEntity<Integer> findCartItemsQuantity(String userId);

    ResponseEntity<List<CartItem>> findCartItems(String userId);

    ResponseEntity<Integer> findCartItemsTotalPrice(String userId);

    ResponseEntity<Void> deleteFromCart(String bookingId, String userId);

    ResponseEntity<CartItem> decreaseCartItem(AddToCartRequest request);

    ResponseEntity<List<CartItem>> addAllCartItems(List<AddAllToCartRequest> request, String userId);

    ResponseEntity<?> findOrderById(String orderId);

    ResponseEntity<Void> deleteAllCartItems(String userId);
}

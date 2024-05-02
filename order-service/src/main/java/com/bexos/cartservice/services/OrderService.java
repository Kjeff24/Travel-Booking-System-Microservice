package com.bexos.cartservice.services;

import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.models.CartItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    ResponseEntity<CartItem> addToCart(AddToCartRequest request);

    ResponseEntity<Integer> findCartItemsQuantity(String userId);

    ResponseEntity<List<CartItem>> findCartItems(String userId);
}

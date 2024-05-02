package com.bexos.cartservice.services;

import com.bexos.cartservice.dto.AddToCartRequest;
import com.bexos.cartservice.models.OrderItem;
import org.springframework.http.ResponseEntity;

public interface OrderItemService {

    ResponseEntity<OrderItem> addToCart(AddToCartRequest request);
}

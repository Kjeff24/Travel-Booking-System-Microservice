package com.bexos.cartservice.repositories;

import com.bexos.cartservice.models.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends MongoRepository<CartItem, String> {
    Optional<CartItem> findByBookingIdAndOrderId(String bookingId, String orderId);

    List<CartItem> findAllByOrderId(String orderId);
}

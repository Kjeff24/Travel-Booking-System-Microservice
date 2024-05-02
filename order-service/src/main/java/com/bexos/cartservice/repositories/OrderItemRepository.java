package com.bexos.cartservice.repositories;

import com.bexos.cartservice.models.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
    Optional<OrderItem> findByBookingId(String bookingId);
}

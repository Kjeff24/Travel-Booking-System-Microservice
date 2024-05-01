package com.bexos.cartservice.repositories;

import com.bexos.cartservice.models.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
}

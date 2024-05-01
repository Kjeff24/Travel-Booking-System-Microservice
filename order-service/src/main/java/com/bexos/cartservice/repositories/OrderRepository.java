package com.bexos.cartservice.repositories;

import com.bexos.cartservice.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    boolean existsByUserId(String userId);
    Optional<Order> findByUserId(String userId);
}

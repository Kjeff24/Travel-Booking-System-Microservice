package com.bexos.cartservice.repositories;

import com.bexos.cartservice.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}

package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByCode(Category code);
}

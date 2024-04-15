package com.bexos.bookingservice.repositories;

import com.bexos.bookingservice.models.Category;
import com.bexos.bookingservice.models.CategoryCode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByCode(CategoryCode code);
}

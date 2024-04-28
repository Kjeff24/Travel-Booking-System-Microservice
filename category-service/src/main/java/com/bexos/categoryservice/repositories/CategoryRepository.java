package com.bexos.categoryservice.repositories;


import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByCode(CategoryCode code);
}

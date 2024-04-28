package com.bexos.categoryservice.services;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<List<Category>> findAllCategories();

    ResponseEntity<Category> createCategory(CategoryRequest categoryRequest);

    ResponseEntity<Category> findCategoryById(ObjectId id);

    ResponseEntity<List<Category>> findCategoryByCode(CategoryCode code);
}

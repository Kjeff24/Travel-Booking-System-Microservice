package com.bexos.categoryservice.services;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.dto.CategoryResponse;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    ResponseEntity<List<Category>> findAllCategories();

    ResponseEntity<Category> createCategory(CategoryRequest categoryRequest);

    ResponseEntity<?> findCategoryById(String id);

    ResponseEntity<List<Category>> findCategoryByCode(CategoryCode code);

    boolean existCategoryById(String id);

    ResponseEntity<?> updateCategory(String bookingId, CategoryRequest request);
}

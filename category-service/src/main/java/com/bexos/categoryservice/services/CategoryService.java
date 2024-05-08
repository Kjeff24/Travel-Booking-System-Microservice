package com.bexos.categoryservice.services;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.dto.ImageModel;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<List<Category>> findAllCategories();

    ResponseEntity<?> createCategory(CategoryRequest categoryRequest, ImageModel icon);

    ResponseEntity<?> findCategoryById(String id);

    ResponseEntity<List<Category>> findCategoryByCode(CategoryCode code);

    boolean existCategoryById(String id);

    ResponseEntity<?> updateCategory(String bookingId, CategoryRequest request, ImageModel icon);
}

package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.CategoryRequest;
import com.bexos.bookingservice.models.Category;
import com.bexos.bookingservice.models.CategoryCode;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<List<Category>> findAllCategories();

    ResponseEntity<Category> createCategory(CategoryRequest categoryRequest);

    ResponseEntity<Category> findCategoryById(ObjectId id);

    ResponseEntity<List<Category>> findCategoryByCode(CategoryCode code);
}

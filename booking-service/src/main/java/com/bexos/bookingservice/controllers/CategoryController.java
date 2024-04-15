package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.CategoryRequest;
import com.bexos.bookingservice.models.Category;
import com.bexos.bookingservice.services.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @GetMapping
    public ResponseEntity<List<Category>> findAllCategories() {
        return categoryServiceImpl.findAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable ObjectId id) {
        return categoryServiceImpl.findCategoryById(id);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Category> findCategoryByCode(@PathVariable Category code) {
        return categoryServiceImpl.findCategoryByCode(code);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryServiceImpl.createCategory(categoryRequest);
    }
}

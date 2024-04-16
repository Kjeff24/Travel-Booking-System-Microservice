package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.CategoryRequest;
import com.bexos.bookingservice.models.Category;
import com.bexos.bookingservice.models.CategoryCode;
import com.bexos.bookingservice.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/code")
    public ResponseEntity<?> findCategoryByCode(@RequestParam("code-name") String code) {
        try {
            CategoryCode newCode = CategoryCode.valueOf(code.toUpperCase());
            return categoryServiceImpl.findCategoryByCode(newCode);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid category code: " + code + ". It should be either ACC, FLI, CAR, or HOT", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable ObjectId id) {
        return categoryServiceImpl.findCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        try {
            CategoryCode newCode = CategoryCode.valueOf(categoryRequest.code().toUpperCase());
            return categoryServiceImpl.createCategory(categoryRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid category code: " + categoryRequest.code() + ". It should be either ACC, FLI, CAR, or HOT", HttpStatus.BAD_REQUEST);
        }
    }
}

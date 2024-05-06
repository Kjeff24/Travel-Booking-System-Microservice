package com.bexos.categoryservice.controllers;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import com.bexos.categoryservice.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-service")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from category controller";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/by-code")
    public ResponseEntity<?> findCategoryByCode(@RequestParam("code-name") String code) {
        try {
            CategoryCode newCode = CategoryCode.valueOf(code.toUpperCase());
            return categoryService.findCategoryByCode(newCode);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid category code: " + code + ". It should be either ACC, FLI, CAR, or HOT", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable String id) {
        return categoryService.findCategoryById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        try {
            System.out.println("Creating new category: " + categoryRequest);
            return categoryService.createCategory(categoryRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid category code: " + categoryRequest.code() + ". It should be either ACC, FLI, CAR, or HOT", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/exists-by-id/{id}")
    public boolean existsCategoryById(@PathVariable String id) {
        return categoryService.existCategoryById(id);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable String categoryId, @RequestBody CategoryRequest request) {
        return categoryService.updateCategory(categoryId, request);
    }
}

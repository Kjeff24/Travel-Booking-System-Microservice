package com.bexos.categoryservice.controllers;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import com.bexos.categoryservice.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-service")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/hello")
    public String hello(){
        return "Hello fron category controller";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAllCategories() {
        return categoryServiceImpl.findAllCategories();
    }

    @GetMapping("/by-code")
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

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        try {
            CategoryCode newCode = CategoryCode.valueOf(categoryRequest.code().toUpperCase());
            return categoryServiceImpl.createCategory(categoryRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid category code: " + categoryRequest.code() + ". It should be either ACC, FLI, CAR, or HOT", HttpStatus.BAD_REQUEST);
        }
    }
}

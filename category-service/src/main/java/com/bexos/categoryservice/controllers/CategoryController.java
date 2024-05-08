package com.bexos.categoryservice.controllers;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.dto.ImageModel;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import com.bexos.categoryservice.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCategory(
            @Valid @RequestPart CategoryRequest categoryRequest,
            @RequestPart("imageFile") MultipartFile image) {
        try {
            ImageModel icon = uploadImage(image);
            return categoryService.createCategory(categoryRequest, icon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Image file upload failed, ensure you upload an image");
        }

    }

    public ImageModel uploadImage(MultipartFile file) throws IOException {
        return ImageModel.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .picByte(file.getBytes())
                .build();

    }

    @GetMapping("/exists-by-id/{id}")
    public boolean existsCategoryById(@PathVariable String id) {
        return categoryService.existCategoryById(id);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable String categoryId,
                                            @RequestBody CategoryRequest request,
                                            @RequestPart("imageFile") MultipartFile image) {
        try {
            ImageModel icon = uploadImage(image);
            return categoryService.updateCategory(categoryId, request, icon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Image file upload failed, ensure you upload an image");
        }
    }
}

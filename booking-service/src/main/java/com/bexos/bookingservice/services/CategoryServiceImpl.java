package com.bexos.bookingservice.services;

import com.bexos.bookingservice.dto.CategoryRequest;
import com.bexos.bookingservice.models.Category;
import com.bexos.bookingservice.models.CategoryCode;
import com.bexos.bookingservice.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity<List<Category>> findAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    public ResponseEntity<Category> createCategory(CategoryRequest categoryRequest) {

        Category category = Category.builder()
                .name(categoryRequest.name())
                .description(categoryRequest.description())
                .code(CategoryCode.valueOf(categoryRequest.code()))
                .icon(categoryRequest.icon())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
    }

    public ResponseEntity<Category> findCategoryById(ObjectId id) {

        return categoryRepository.findById(String.valueOf(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Category>> findCategoryByCode(CategoryCode code) {
        List<Category> categories = categoryRepository.findAllByCode(code);

        return ResponseEntity.ok(categories);
    }
}

package com.bexos.categoryservice.services;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.dto.CategoryResponse;
import com.bexos.categoryservice.mappers.CategoryMapper;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import com.bexos.categoryservice.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

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

    public ResponseEntity<?> findCategoryById(String id) {
        Optional<CategoryResponse> response = categoryRepository.findById(id)
                .map(categoryMapper::toCategoryResponse);
        if (response.isPresent()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Category with "+ id +" was not found");
    }

    public ResponseEntity<List<Category>> findCategoryByCode(CategoryCode code) {
        List<Category> categories = categoryRepository.findAllByCode(code);

        return ResponseEntity.ok(categories);
    }

    public boolean existCategoryById(String id) {
        return categoryRepository.existsById(String.valueOf(id));
    }
}

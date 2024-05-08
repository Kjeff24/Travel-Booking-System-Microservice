package com.bexos.categoryservice.services;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.dto.CategoryResponse;
import com.bexos.categoryservice.dto.ImageModel;
import com.bexos.categoryservice.mappers.CategoryMapper;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import com.bexos.categoryservice.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
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

    public ResponseEntity<?> createCategory(CategoryRequest categoryRequest, ImageModel icon) {

        Category category = Category.builder()
                .name(categoryRequest.name())
                .description(categoryRequest.description())
                .code(CategoryCode.valueOf(categoryRequest.code()))
                .icon(icon)
                .build();

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    public ResponseEntity<?> findCategoryById(String id) {
        Optional<CategoryResponse> response = categoryRepository.findById(id)
                .map(categoryMapper::toCategoryResponse);
        if (response.isPresent()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Category with " + id + " was not found");
    }

    public ResponseEntity<List<Category>> findCategoryByCode(CategoryCode code) {
        List<Category> categories = categoryRepository.findAllByCode(code);

        return ResponseEntity.ok(categories);
    }

    public boolean existCategoryById(String id) {
        return categoryRepository.existsById(String.valueOf(id));
    }

    public ResponseEntity<?> updateCategory(String categoryId, CategoryRequest request, ImageModel icon) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category categoryToUpdate = category.get();
            categoryToUpdate.setCode(CategoryCode.valueOf(request.code()));
            categoryToUpdate.setIcon(icon);
            categoryToUpdate.setName(request.name());
            categoryToUpdate.setDescription(request.description());
            return ResponseEntity.ok(categoryRepository.save(categoryToUpdate));
        }
        return ResponseEntity.badRequest().body("Category does not exist");
    }


}

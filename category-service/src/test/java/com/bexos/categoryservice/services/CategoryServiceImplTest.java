package com.bexos.categoryservice.services;

import com.bexos.categoryservice.dto.CategoryRequest;
import com.bexos.categoryservice.dto.CategoryResponse;
import com.bexos.categoryservice.dto.ImageModel;
import com.bexos.categoryservice.mappers.CategoryMapper;
import com.bexos.categoryservice.models.Category;
import com.bexos.categoryservice.models.CategoryCode;
import com.bexos.categoryservice.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllCategories() {
        List<Category> accommodations = List.of(new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(accommodations);

        ResponseEntity<?> response = categoryService.findAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accommodations, response.getBody());
    }

    @Test
    void createCategory() {
        CategoryRequest categoryRequest = CategoryRequest.builder().name("Accommodation").code("ACC").build();
        ImageModel icon = ImageModel.builder().build();
        Category category = Category.builder().name(categoryRequest.name()).code(CategoryCode.valueOf(categoryRequest.code())).build();

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        ResponseEntity<?> responseEntity = categoryService.createCategory(categoryRequest, icon);

        assertEquals(category, responseEntity.getBody());
    }


    @Test
    public void findCategoryById_IdFound() {
        String id = "testId";
        CategoryResponse categoryResponse = CategoryResponse.builder().name("Accommodation").build();
        Category category = Category.builder().id(id).build();
        when(categoryRepository.findById(id)).thenReturn(Optional.ofNullable(category));
        when(categoryMapper.toCategoryResponse(any())).thenReturn(categoryResponse);

        ResponseEntity<?> response = categoryService.findCategoryById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryMapper, times(1)).toCategoryResponse(any());
    }

    @Test
    public void findCategoryByIdNotFound() {
        String id = "testId";

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = categoryService.findCategoryById(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Category with " + id + " was not found", response.getBody());

        verify(categoryRepository, times(1)).findById(id);
        verify(categoryMapper, times(0)).toCategoryResponse(any());
    }

    @Test
    public void findCategoryByCode() {
        CategoryCode code = CategoryCode.ACC;
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAllByCode(code)).thenReturn(categories);

        ResponseEntity<List<Category>> response = categoryService.findCategoryByCode(code);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());

        verify(categoryRepository, times(1)).findAllByCode(code);
    }

    @Test
    void existCategoryById() {
        String id = "testId";
        when(categoryRepository.existsById(id)).thenReturn(true);

        boolean exists = categoryService.existCategoryById(id);

        assertTrue(exists);
        verify(categoryRepository, times(1)).existsById(id);
    }

    @Test
    void updateCategory_CategoryExists() {
        // Mocking
        String categoryId = "1";
        CategoryRequest request = new CategoryRequest("Updated Name", "Updated Description", "ACC");
        ImageModel icon = ImageModel.builder().build();
        Category categoryToUpdate = new Category();
        Optional<Category> optionalCategory = Optional.of(categoryToUpdate);
        when(categoryRepository.findById(categoryId)).thenReturn(optionalCategory);
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryToUpdate);

        ResponseEntity<?> responseEntity = categoryService.updateCategory(categoryId, request, icon);

        assertEquals(ResponseEntity.ok(categoryToUpdate), responseEntity);
        assertEquals("Updated Name", categoryToUpdate.getName());
        assertEquals("Updated Description", categoryToUpdate.getDescription());
        assertEquals(CategoryCode.ACC, categoryToUpdate.getCode());
        assertEquals(icon, categoryToUpdate.getIcon());
    }

    @Test
    void UpdateCategory_CategoryNotExists() {
        String categoryId = "1";
        CategoryRequest request = new CategoryRequest("Updated Name", "Updated Description", "UPDATED_CODE");
        ImageModel icon = ImageModel.builder().build();
        Optional<Category> optionalCategory = Optional.empty();
        when(categoryRepository.findById(categoryId)).thenReturn(optionalCategory);

        ResponseEntity<?> responseEntity = categoryService.updateCategory(categoryId, request, icon);

        assertEquals(ResponseEntity.badRequest().body("Category does not exist"), responseEntity);
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
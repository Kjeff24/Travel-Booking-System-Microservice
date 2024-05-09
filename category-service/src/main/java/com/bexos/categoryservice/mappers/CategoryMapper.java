package com.bexos.categoryservice.mappers;

import com.bexos.categoryservice.dto.CategoryResponse;
import com.bexos.categoryservice.dto.ImageModel;
import com.bexos.categoryservice.models.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .code(category.getCode())
                .icon(category.getIcon())
                .build();
    }

}

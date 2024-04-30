package com.bexos.categoryservice.dto;

import com.bexos.categoryservice.models.CategoryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CategoryResponse(
        String id,
        String name,
        String description,
        CategoryCode code,
        String icon
) {
}

package com.bexos.bookingservice.dto;

import com.bexos.bookingservice.models.CategoryCode;

public record CategoryRequest(
        String name,
        String description,
        CategoryCode code,
        String icon
) {
}

package com.bexos.categoryservice.dto;

import lombok.Builder;

@Builder
public record ImageModel(
        String name,
        String type,
        byte[] picByte
) {
}

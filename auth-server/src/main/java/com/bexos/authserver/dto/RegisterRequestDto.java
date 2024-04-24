package com.bexos.authserver.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record RegisterRequestDto(
        String fullName,
        @NotBlank(message = "Username must not be blank")
        String username,
        @NotBlank(message = "Email must not be blank")
        String email,
        String password,
        List<String> roles
) {
}


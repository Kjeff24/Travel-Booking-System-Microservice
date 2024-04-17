package com.bexos.security.payload;

// Data carrier for registration
public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {}

package com.bexos.authserver.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {}


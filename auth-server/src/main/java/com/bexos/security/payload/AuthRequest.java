package com.bexos.security.payload;

// Data Carrier for authentication request
public record AuthRequest(
        String email,
        String password
) {}

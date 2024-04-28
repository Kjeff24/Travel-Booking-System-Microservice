package com.bexos.authserver.dto;

public record ChangePasswordRequest(
        String userId,
        String oldPassword,
        String newPassword
) {
}

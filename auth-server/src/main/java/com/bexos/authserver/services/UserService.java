package com.bexos.authserver.services;

import com.bexos.authserver.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> register(RegisterRequest request);
}

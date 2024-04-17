package com.bexos.security.services;

import com.bexos.security.payload.AuthRequest;
import com.bexos.security.payload.RegisterRequest;
import com.bexos.security.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(RegisterRequest request);

    ResponseEntity<?> authenticate(AuthRequest request);
}

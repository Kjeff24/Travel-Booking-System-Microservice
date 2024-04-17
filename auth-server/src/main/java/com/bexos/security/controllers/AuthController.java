package com.bexos.security.controllers;

import com.bexos.security.payload.AuthRequest;
import com.bexos.security.payload.RegisterRequest;
import com.bexos.security.responses.AuthenticationResponse;
import com.bexos.security.services.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return authServiceImpl.confirmEmail(confirmationToken);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authServiceImpl.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> register(
            @RequestBody AuthRequest request
    ){
        return ResponseEntity.ok(authServiceImpl.authenticate(request));
    }

    @GetMapping
    public String hello(){
        return "hello free auth";
    }
}
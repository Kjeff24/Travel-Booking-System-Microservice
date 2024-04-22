package com.bexos.authserver.mappers;

import com.bexos.authserver.dto.RegisterRequestDto;
import com.bexos.authserver.models.Role;
import com.bexos.authserver.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toUser(RegisterRequestDto request){

        return User.builder()
                .fullName(request.fullName())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
    }
}

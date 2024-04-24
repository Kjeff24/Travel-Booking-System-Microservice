package com.bexos.authserver.mappers;

import com.bexos.authserver.dto.RegisterRequestDto;
import com.bexos.authserver.enums.RoleName;
import com.bexos.authserver.models.Role;
import com.bexos.authserver.models.User;
import com.bexos.authserver.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User toUser(RegisterRequestDto request) {

        Set<Role> roles = new HashSet<>();
        request.roles().forEach(r -> {
            Role role = roleRepository.findByRole(RoleName.valueOf(r))
                    .orElseThrow(() -> new RuntimeException("role not found"));
            roles.add(role);
        });
        return User.builder()
                .fullName(request.fullName())
                .username(request.username())
                .email(request.email())
                .roles(roles)
                .password(passwordEncoder.encode(request.password()))
                .build();
    }

}

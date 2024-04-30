package com.bexos.authserver.mappers;

import com.bexos.authserver.dto.FormRegisterDto;
import com.bexos.authserver.dto.RegisterRequestDto;
import com.bexos.authserver.enums.RoleName;
import com.bexos.authserver.models.Password;
import com.bexos.authserver.models.Role;
import com.bexos.authserver.models.User;
import com.bexos.authserver.repositories.PasswordRepository;
import com.bexos.authserver.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toUser(FormRegisterDto user){
        Role customerRole = Role.builder().role(RoleName.CUSTOMER).build();
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        return User.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

    }

    public Password createPassword(User user){
        return Password.builder()
                .userId(user.getId())
                .userPasswords(Collections.singletonList(user.getPassword()))
                .build();
    }

}

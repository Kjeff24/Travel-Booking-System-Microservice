package com.bexos.authserver.services;

import com.bexos.authserver.dto.FormChangePasswordDto;
import com.bexos.authserver.dto.FormRegisterDto;
import com.bexos.authserver.enums.RoleName;
import com.bexos.authserver.mappers.UserMapper;
import com.bexos.authserver.models.ConfirmationToken;
import com.bexos.authserver.models.Password;
import com.bexos.authserver.models.Role;
import com.bexos.authserver.models.User;
import com.bexos.authserver.repositories.ConfirmationTokenRepository;
import com.bexos.authserver.repositories.PasswordRepository;
import com.bexos.authserver.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    private PasswordRepository passwordRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void form_register() {
        FormRegisterDto dto = new FormRegisterDto("Jeffery", "kjeff", "jeffreyarthur123@gmail.com", "A.sdf12345");
        Role customerRole = Role.builder().role(RoleName.CUSTOMER).build();
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        User newUser = User.builder()
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .roles(roles)
                .password(dto.getPassword())
                .build();
        Password newPassword = Password.builder()
                .userId(newUser.getId())
                .userPasswords(Collections.singletonList(newUser.getPassword()))
                .build();
        ConfirmationToken newConfirmationToken = ConfirmationToken.builder()
                .user(newUser)
                .confirmationToken(UUID.randomUUID().toString())
                .build();
        System.out.println(newConfirmationToken);

        when(userMapper.toUser(dto)).thenReturn(newUser);
        when(userMapper.createPassword(newUser)).thenReturn(newPassword);
        when(userRepository.save(newUser)).thenReturn(newUser);
        when(passwordRepository.save(newPassword)).thenReturn(newPassword);
        when(confirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(newConfirmationToken);

        userService.form_register(dto);

        verify(userRepository).save(newUser);
        verify(passwordRepository).save(newPassword);
        verify(confirmationTokenRepository, times(1)).save(any(ConfirmationToken.class));
        verify(emailService, times(1)).sendEmail(any(SimpleMailMessage.class));

    }

    @Test
    void changePassword() {
//        String userId = "testUserId";
//        User user = User.builder()
//                .password("oldPassword")
//                .build();
//        Password existingPassword = Password.builder()
//                .userId(userId)
//                .userPasswords(List.of("oldPassword"))
//                .build();
//        FormChangePasswordDto formChangePasswordDto = new FormChangePasswordDto();
//        formChangePasswordDto.setOldPassword("oldPassword");
//        formChangePasswordDto.setNewPassword("newPassword");
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(passwordRepository.findByUserId(userId)).thenReturn(existingPassword);
//
//        // Act
//        Map<String, String> result = userService.changePassword(formChangePasswordDto, userId);
//
//        // Assert
//        verify(userRepository, times(1)).findById(userId);
//        verify(passwordRepository, times(1)).findByUserId(userId);
//        assertEquals("Password changed successfully", result.get("success"));
    }

    @Test
    void confirmEmail() {
    }

    @Test
    void isValidPassword() {
    }
}
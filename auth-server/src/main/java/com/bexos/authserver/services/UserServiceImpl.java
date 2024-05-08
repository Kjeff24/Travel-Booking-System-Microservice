package com.bexos.authserver.services;

import com.bexos.authserver.dto.FormChangePasswordDto;
import com.bexos.authserver.dto.FormRegisterDto;
import com.bexos.authserver.mappers.UserMapper;
import com.bexos.authserver.models.ConfirmationToken;
import com.bexos.authserver.models.Password;
import com.bexos.authserver.models.User;
import com.bexos.authserver.repositories.ConfirmationTokenRepository;
import com.bexos.authserver.repositories.PasswordRepository;
import com.bexos.authserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordRepository passwordRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[^a-zA-Z0-9 ])" +
                    "(?=\\S+$)" +
                    ".{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public void form_register(FormRegisterDto user) {

        User newUser = userRepository.save(userMapper.toUser(user));
        passwordRepository.save(userMapper.createPassword(newUser));

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .user(newUser)
                .confirmationToken(UUID.randomUUID().toString())
                .build();
        ConfirmationToken emailConfirmationToken = confirmationTokenRepository.save(confirmationToken);
        emailService.sendEmail(createEmail(newUser, emailConfirmationToken));
    }

    public Map<String, String> changePassword(FormChangePasswordDto passwordField, String userId) {
        Map<String, String> message = new HashMap<>();
        User updateUser = userRepository.findById(userId).orElse(null);
        if (updateUser == null) {
            message.put("error", "User not found");
            return message;
        }
        if (updateUser.getPassword() == null) {
            message.put("error", "User does not have a password set");
            return message;
        }
        if (!passwordEncoder.matches(passwordField.getOldPassword(), updateUser.getPassword())) {
            message.put("error", "Old password is incorrect");
            return message;
        }

        Password existingPassword = passwordRepository.findByUserId(userId);
        if (isPasswordUsed(existingPassword, passwordField.getNewPassword())) {
            message.put("error", "Password has already been used");
        }else if (!isValidPassword(passwordField.getNewPassword())) {
            message.put("error", "New Password must be at least 8 characters long and include a combination of uppercase letters, lowercase letters, special characters, and numbers.");
        } else {
            updatePassword(updateUser, existingPassword, passwordField.getNewPassword());
            message.put("success", "Password changed successfully");
        }

        return message;

    }

    private SimpleMailMessage createEmail(User user, ConfirmationToken confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

        return mailMessage;
    }

    public Map<String, String> confirmEmail(String confirmationToken) {
        Map<String, String> message = new HashMap<>();
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token == null) {
            message.put("error", "Couldn't verify email");
        } else {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail()).orElse(null);
            if (user != null) {
                user.setEnabled(true);
                userRepository.save(user);
                message.put("success", "Email verified Successfully");
            } else {
                message.put("error", "User not found.");
            }
        }
        return message;
    }

    public boolean isValidPassword(final String password) {
        return pattern.matcher(password).matches();
    }
    private boolean isPasswordUsed(Password existingPassword, String newPassword) {
        return existingPassword.getUserPasswords().stream()
                .anyMatch(oldPassword -> passwordEncoder.matches(newPassword, oldPassword));
    }

    private void updatePassword(User updateUser, Password existingPassword, String newPassword) {
        updateUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(updateUser);
        existingPassword.getUserPasswords().add(passwordEncoder.encode(newPassword));

        passwordRepository.save(existingPassword);
    }

}

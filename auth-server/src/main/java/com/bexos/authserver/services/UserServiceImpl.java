package com.bexos.authserver.services;

import com.bexos.authserver.dto.ChangePasswordRequest;
import com.bexos.authserver.dto.FormRegisterDto;
import com.bexos.authserver.dto.RegisterRequestDto;
import com.bexos.authserver.enums.RoleName;
import com.bexos.authserver.mappers.UserMapper;
import com.bexos.authserver.models.ConfirmationToken;
import com.bexos.authserver.models.User;
import com.bexos.authserver.repositories.ConfirmationTokenRepository;
import com.bexos.authserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void form_register(FormRegisterDto user){

        User newUser = userRepository.save(userMapper.registerUser(user));
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .user(newUser)
                .confirmationToken(UUID.randomUUID().toString())
                .build();
        ConfirmationToken emailConfirmationToken = confirmationTokenRepository.save(confirmationToken);
        emailService.sendEmail(createEmail(newUser, emailConfirmationToken));
    }

    public ResponseEntity<?> register(RegisterRequestDto request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = userRepository.save(userMapper.toUser(request));
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .user(user)
                .confirmationToken(UUID.randomUUID().toString())
                .build();
        ConfirmationToken emailConfirmationToken = confirmationTokenRepository.save(confirmationToken);
        emailService.sendEmail(createEmail(user, emailConfirmationToken));

        return ResponseEntity.ok("Verify email by the link sent to your email address");
    }

    public ResponseEntity<String> changePassword(ChangePasswordRequest request) {
        Optional<User> user = userRepository.findById(request.userId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }

        if(!isValidPassword(request.newPassword())){
            return ResponseEntity.badRequest().body("New Password must be at least 8 characters long and include a combination of uppercase letters, lowercase letters, special characters, and numbers.");
        }

        User updateUser = user.get();

        boolean checkPassword = isMatchingPassword(updateUser, request.oldPassword());

        if(checkPassword) {
            updateUser.setPassword(passwordEncoder.encode(request.newPassword()));
            userRepository.save(updateUser);
            return ResponseEntity.ok("Password changed successfully!");
        }

        return ResponseEntity.badRequest().body("Error: Passwords do not match!");

    }

    private SimpleMailMessage createEmail(User user, ConfirmationToken confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

        return mailMessage;
    }

    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token == null) {
            return ResponseEntity.badRequest().body("Error: Couldn't verify email");
        }

        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnabled(true);
            userRepository.save(user);

            return ResponseEntity.ok("Email verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Error: User not found");
        }
    }

    private boolean isMatchingPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        boolean containsSpecialCharacter = false;
        boolean containsDigit = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                containsUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                containsLowerCase = true;
            } else if (Character.isDigit(ch)) {
                containsDigit = true;
            } else {
                containsSpecialCharacter = true;
            }
        }

        return containsUpperCase && containsLowerCase && containsSpecialCharacter && containsDigit;
    }
}

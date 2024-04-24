package com.bexos.authserver.services;

import com.bexos.authserver.dto.RegisterRequestDto;
import com.bexos.authserver.mappers.UserMapper;
import com.bexos.authserver.models.ConfirmationToken;
import com.bexos.authserver.models.User;
import com.bexos.authserver.repositories.ConfirmationTokenRepository;
import com.bexos.authserver.repositories.RoleRepository;
import com.bexos.authserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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

    public ResponseEntity<?> register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = userRepository.save(userMapper.toUser(request));
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .user(user)
                .confirmationToken(UUID.randomUUID().toString())
                .build();
        ConfirmationToken emailConfirmationToken = confirmationTokenRepository.save(confirmationToken);
        emailService.sendEmail(createEmail(user, emailConfirmationToken));

        return ResponseEntity.ok("Verify email by the link sent on your email address");
    }

    public SimpleMailMessage createEmail(User user, ConfirmationToken confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/api/v1/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());

        return mailMessage;
    }

    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token == null) {
            return ResponseEntity.badRequest().body("Error: Couldn't verify email");
        }

        Optional<User> userOptional = userRepository.findByEmail(token.getUser().getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnabled(true);
            userRepository.save(user);

            return ResponseEntity.ok("Email verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Error: User not found");
        }
    }
}

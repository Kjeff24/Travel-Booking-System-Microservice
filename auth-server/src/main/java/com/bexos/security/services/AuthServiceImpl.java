package com.bexos.security.services;

import com.bexos.security.jwt_authentication.JwtServiceImpl;
import com.bexos.security.mapper.UserMapper;
import com.bexos.security.models.ConfirmationToken;
import com.bexos.security.models.User;
import com.bexos.security.payload.AuthRequest;
import com.bexos.security.payload.RegisterRequest;
import com.bexos.security.repositories.ConfirmationTokenRepository;
import com.bexos.security.repositories.UserRepository;
import com.bexos.security.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;


    public ResponseEntity<?> register(RegisterRequest request) {
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
            user.setEnabled(true); // Assuming 'enabled' is the correct property name
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email verified successfully!");
            response.put("token", jwtToken);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Error: User not found");
        }
    }

    public ResponseEntity<?> authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            Optional<User> userOptional = userRepository.findByEmail(request.email());
            if (userOptional.isPresent()) {
                var jwtToken = jwtService.generateToken(userOptional.get());
                AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
                return ResponseEntity.ok(authenticationResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Failed to authenticate");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }
}
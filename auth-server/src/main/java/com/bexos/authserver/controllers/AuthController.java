package com.bexos.authserver.controllers;

import com.bexos.authserver.dto.FormRegisterDto;
import com.bexos.authserver.repositories.UserRepository;
import com.bexos.authserver.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public ModelAndView getUsers() {
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public ModelAndView logoutOK() {
        return new ModelAndView("logout");
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        FormRegisterDto user = new FormRegisterDto();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String formRegister(@ModelAttribute("user") FormRegisterDto user, Model model) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            model.addAttribute("registration_error", "User with this email already exists.");

        } else if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            model.addAttribute("registration_error", "User with this username already exists.");

        } else if (!userServiceImpl.isStrongPassword(user.getPassword())) {
            model.addAttribute(
                    "registration_error",
                    "Password must be at least 8 characters long and include a combination of uppercase letters, lowercase letters, special characters, and numbers.");

        } else {
            userServiceImpl.form_register(user);
            model.addAttribute(
                    "success",
                    "Registration was successfull, verification email has been sent to your account.");

        }
        return "signup";
    }


}

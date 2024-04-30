package com.bexos.authserver.controllers;

import com.bexos.authserver.dto.FormChangePasswordDto;
import com.bexos.authserver.dto.FormRegisterDto;
import com.bexos.authserver.repositories.UserRepository;
import com.bexos.authserver.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    public String getUsers() {
        return "welcome";
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
            model.addAttribute("error", "User with this email already exists.");

        } else if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
            model.addAttribute("error", "User with this username already exists.");

        } else if (!userServiceImpl.validate(user.getPassword())) {
            model.addAttribute(
                    "error",
                    "Password must be at least 8 characters long and include a combination of uppercase letters, lowercase letters, special characters, and numbers.");

        } else {
            userServiceImpl.form_register(user);
            model.addAttribute(
                    "success",
                    "Registration was successful, verification email has been sent to your account.");

        }
        return "signup";
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken, Model model) {
        Map<String, String> message = userServiceImpl.confirmEmail(confirmationToken);
        if (message.get("error") != null) {
            model.addAttribute("error", message.get("error"));
        }
        else {
            model.addAttribute("success", message.get("success"));
        }
        return "confirm-email";
    }

    @GetMapping("/change-password/{userId}")
    public String formChangePassword(@PathVariable("userId") String userId, Model model) {
        FormChangePasswordDto passwordRequest = new FormChangePasswordDto();
        model.addAttribute("passwordRequest", passwordRequest);
        model.addAttribute("userId", userId);
        return "change-password";
    }

    @PostMapping("/change-password/{userId}")
    public String formChangePassword(@PathVariable("userId") String userId, @ModelAttribute("passwordRequest") FormChangePasswordDto passwordField, Model model) {
        Map<String, String> message = userServiceImpl.changePassword(passwordField, userId);
        if(message.get("error") != null) {
            model.addAttribute("error", message.get("error"));
        }
        else {
            model.addAttribute("success", message.get("success"));
        }
        return "change-password";
    }


}

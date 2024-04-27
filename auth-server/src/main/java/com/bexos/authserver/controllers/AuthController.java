package com.bexos.authserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {
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
    public ModelAndView signup() {
        return new ModelAndView("signup");
    }

}

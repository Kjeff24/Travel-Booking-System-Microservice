package com.bexos.authserver.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//    @GetMapping
//    public String home() {
//        return "index";
//    }
//
//    @GetMapping("/messages")
//    public String privateMessages(@AuthenticationPrincipal OAuth2User user, Model model) {
//        model.addAttribute("body", user.getAttribute("name"));
//        return "response";
//    }
//
//    @GetMapping("/public/messages")
//    public String publicMessages(Model model) {
//        model.addAttribute("body", "nobody");
//        return "response";
//    }

}

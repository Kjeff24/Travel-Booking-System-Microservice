package com.bexos.bookingservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/v2/categories/all")
    public String hello(){
        return "Hello World";
    }
}

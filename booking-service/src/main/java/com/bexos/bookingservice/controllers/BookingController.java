package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking-service")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("hello")
    public String hello() {
        return "Welcome to accommodation service secured";
    }

    @GetMapping("/find-by-id/{bookingId}")
    public ResponseEntity<?> findBookingOfferById(@PathVariable String bookingId) {
        return bookingService.findBookingOfferById(bookingId);
    }

    @GetMapping("/number-of-products")
    public ResponseEntity<?> findNumberOfProducts() {
        return bookingService.findNumberOfProducts();
    }
}

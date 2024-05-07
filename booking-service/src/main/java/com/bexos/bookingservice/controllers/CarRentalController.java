package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.dto.CarRentalRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.models.booking_categories.CarRental;
import com.bexos.bookingservice.services.CarRentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-service/car-rental")
@RequiredArgsConstructor
public class CarRentalController {
    private final CarRentalService carRentalService;

    @GetMapping
    public ResponseEntity<List<CarRental>> findAllCarRentals() {
        return carRentalService.findAllCarRentals();
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<CarRental> findCarRentalById(@PathVariable String bookingId) {
        return carRentalService.findCarRentalById(bookingId);
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> findAllCarRentalsByCategory(@PathVariable String categoryId) {
        return carRentalService.findAllCarRentalsByCategory(categoryId);
    }

    @GetMapping("/find-category-by-booking-id/{bookingId}")
    public ResponseEntity<?> findCategoryByBookingId(@PathVariable String bookingId) {
        return carRentalService.findCategoryByBookingId(bookingId);
    }

    @PostMapping
    public ResponseEntity<?> createCarRentalOffer(@Valid @RequestBody CarRentalRequest carRentalRequest) {
        return carRentalService.createCarRentalOffer(carRentalRequest);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateCarRental(@PathVariable String bookingId, @RequestBody CarRentalRequest request) {
        return carRentalService.updateCarRental(bookingId, request);
    }
}

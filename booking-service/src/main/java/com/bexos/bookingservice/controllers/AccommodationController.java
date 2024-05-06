package com.bexos.bookingservice.controllers;

import com.bexos.bookingservice.dto.AccommodationRequest;
import com.bexos.bookingservice.models.booking_categories.Accommodation;
import com.bexos.bookingservice.services.AccommodationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-service/accommodation")
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping
    public ResponseEntity<List<Accommodation>> findAllAccommodations() {
        return accommodationService.findAllAccommodations();
    }

    @GetMapping("/find-category-by-booking-id/{bookingId}")
    public ResponseEntity<?> findCategoryByBookingId(@PathVariable String bookingId) {
        return accommodationService.findCategoryByBookingId(bookingId);
    }

    @PostMapping
    public ResponseEntity<?> createAccommodationOffer(@Valid @RequestBody AccommodationRequest accommodationRequest) {
        return accommodationService.createAccommodationOffer(accommodationRequest);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Accommodation> findAccommodationById(@PathVariable String bookingId) {
        return accommodationService.findAccommodationById(bookingId);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateAccommodation(@PathVariable String bookingId, @RequestBody AccommodationRequest accommodationRequest) {
        return accommodationService.updateAccommodation(bookingId, accommodationRequest);
    }
}
